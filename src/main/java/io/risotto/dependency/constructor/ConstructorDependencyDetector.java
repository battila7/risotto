package io.risotto.dependency.constructor;

import io.risotto.annotations.Inject;
import io.risotto.dependency.Dependency;
import io.risotto.dependency.DependencyDetector;
import io.risotto.dependency.processor.DependencyProcessor;
import io.risotto.dependency.processor.ProcessorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * {@code ConstructorDependencyDetector} inspects the constructors of a class and looks for the
 * {@link Inject} annotation. If an injectable constructor is found, a new {@link
 * ConstructorDependencyInjector} is created.
 *
 * Dependencies are detected using constructor parameter inspection. Each constructor parameter
 * becomes an immediate dependency.
 *
 * Note that only <b>public</b> constructors are inspected.
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

    try {
      injectableConstructorOptional = getInjectableConstructor();
    } catch (NoSuchMethodException e) {
      logger.warn("Constructor with @Inject found but could not been retrieved.");

      return Optional.empty();
    }

    if (!injectableConstructorOptional.isPresent()) {
      return Optional.empty();
    }

    Constructor<T> injectableConstructor = injectableConstructorOptional.get();

    Optional<List<Dependency<?>>> dependenciesOptional = processParameters(injectableConstructor);

    if (dependenciesOptional.isPresent()) {
      logger.info("Successfully set up constructor injection for {}.", clazz.getCanonicalName());

      dependencyInjector =
          new ConstructorDependencyInjector<>(clazz, dependenciesOptional.get(),
              injectableConstructor);
    }

    return dependenciesOptional;
  }

  private Optional<Constructor<T>> getInjectableConstructor() throws NoSuchMethodException {
    Constructor<?>[] constructors = clazz.getConstructors();

    if (constructors.length != 1) {
      return Optional.empty();
    }

    Constructor<?> targetConstructor = constructors[0];

    if (targetConstructor.isAnnotationPresent(Inject.class)) {
      // retrieve Constructor<T> instead of Constructor<?>
      Constructor<T> typedConstructor = clazz.getConstructor(targetConstructor.getParameterTypes());

      return Optional.of(typedConstructor);
    }

    return Optional.empty();
  }

  private Optional<List<Dependency<?>>> processParameters(Constructor<?> constructor) {
    Parameter[] parameters = constructor.getParameters();

    List<Dependency<?>> dependencies = new LinkedList<>();

    DependencyProcessor processorChain = ProcessorChain.getProcessorChain();

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