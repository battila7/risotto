package com.dijon.dependency.management.constructor;

import com.dijon.annotations.Inject;
import com.dijon.annotations.InjectSpecifier;
import com.dijon.annotations.Named;
import com.dijon.dependency.AnnotatedDependency;
import com.dijon.dependency.Dependency;
import com.dijon.dependency.NamedDependency;
import com.dijon.dependency.management.DependencyDetector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ConstructorDependencyDetector<T> extends DependencyDetector<T> {
  private final ParameterProcessor processorChain;

  public ConstructorDependencyDetector(Class<T> clazz) {
    super(clazz);

    processorChain = setUpProcessorChain();
  }

  private ParameterProcessor setUpProcessorChain() {
    ParameterProcessor basicProcessor = new BasicParameterProcessor();

    ParameterProcessor namedProcessor = new NamedParameterProcessor();

    ParameterProcessor annotatedProcessor = new AnnotatedParameterProcessor();

    ParameterProcessor unableProcessor = new UnableProcessor();

    basicProcessor.setSuccessor(namedProcessor);

    namedProcessor.setSuccessor(annotatedProcessor);

    annotatedProcessor.setSuccessor(unableProcessor);

    return basicProcessor;
  }

  @Override
  public Optional<List<Dependency<?>>> detectImmediateDependencies() {
    Optional<Constructor<T>> injectableConstructorOptional;

    try {
      injectableConstructorOptional = getInjectableConstructor();
    } catch (Exception e) {
      return Optional.empty();
    }

    if (!injectableConstructorOptional.isPresent()) {
      return Optional.empty();
    }

    Constructor<T> injectableConstructor = injectableConstructorOptional.get();

    Optional<List<Dependency<?>>> dependenciesOptional = processParameters(injectableConstructor);

    if (dependenciesOptional.isPresent()) {
      dependencyInjector =
          new ConstructorDependencyInjector<>(clazz, dependenciesOptional.get(),
              injectableConstructor);
    }

    return dependenciesOptional;
  }

  private Optional<Constructor<T>> getInjectableConstructor() throws Exception {
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
