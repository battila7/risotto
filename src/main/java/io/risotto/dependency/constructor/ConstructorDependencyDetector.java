package io.risotto.dependency.constructor;

import io.risotto.annotations.Inject;
import io.risotto.dependency.Dependency;
import io.risotto.dependency.DependencyDetector;
import io.risotto.dependency.processor.DependencyProcessor;
import io.risotto.dependency.processor.ProcessorChain;
import io.risotto.reflection.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * {@code ConstructorDependencyDetector} inspects the constructors of a class and looks for the
 * {@link Inject} annotation. If an injectable constructor is found, a new {@link
 * ConstructorDependencyInjector} is created. <p> Dependencies are detected using constructor
 * parameter inspection. Each constructor parameter becomes an immediate dependency. <p> Note that
 * only <b>public</b> constructors are inspected.
 * @param <T> the type to dependency detect
 */
public class ConstructorDependencyDetector<T> extends DependencyDetector<T> {
  private static final Logger logger = LoggerFactory.getLogger(ConstructorDependencyDetector.class);

  /**
   * Constructs a new instance that will be used to detect the dependencies of the specified class.
   * @param clazz the dependency detectable class
   */
  public ConstructorDependencyDetector(Class<T> clazz) {
    super(clazz);
  }

  @Override
  public Optional<List<Dependency<?>>> detectImmediateDependencies() {
    Optional<Constructor<T>> injectableConstructorOptional;

    injectableConstructorOptional = getInjectableConstructor();

    if (!injectableConstructorOptional.isPresent()) {
      return Optional.empty();
    }

    Constructor<T> injectableConstructor = injectableConstructorOptional.get();

    Optional<List<Dependency<?>>> dependenciesOptional = processParameters(injectableConstructor);

    if (dependenciesOptional.isPresent()) {
      logger.debug("Successfully set up constructor injection for {} using constructor {}.", clazz,
          injectableConstructor);

      dependencyInjector =
          new ConstructorDependencyInjector<>(clazz, dependenciesOptional.get(),
              injectableConstructor);
    }

    return dependenciesOptional;
  }

  @SuppressWarnings("unchecked")
  private Optional<Constructor<T>> getInjectableConstructor() {
    List<Constructor<?>> injectableConstructors = getInjectableConstructorList();

    if (injectableConstructors.size() != 1) {
      logger.debug("Zero or more than one injectable constructors found, detection failed.");

      return Optional.empty();
    }

    Constructor<?> targetConstructor = injectableConstructors.get(0);

    return Optional.of((Constructor<T>) targetConstructor);
  }

  private List<Constructor<?>> getInjectableConstructorList() {
    return Arrays.stream(clazz.getConstructors())
        .filter(ReflectionUtils::isInjectDirectlyPresent)
        .filter(ReflectionUtils::isPublicNotStaticNotFinal)
        .collect(Collectors.toList());
  }

  private Optional<List<Dependency<?>>> processParameters(Constructor<?> constructor) {
    Parameter[] parameters = constructor.getParameters();

    List<Dependency<?>> dependencies = new LinkedList<>();

    DependencyProcessor processorChain = ProcessorChain.getProcessorChain();

    logger.debug("Processing parameters of injectable constructor {}", constructor);

    for (Parameter parameter : parameters) {
      Optional<Dependency<?>> dependencyOptional = processorChain.process(parameter);

      if (!dependencyOptional.isPresent()) {
        return Optional.empty();
      }

      dependencies.add(dependencyOptional.get());
    }

    return Optional.of(dependencies);
  }
}
