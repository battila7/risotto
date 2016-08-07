package com.dijon.dependency.management;

import com.dijon.annotations.Inject;
import com.dijon.dependency.Dependency;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Optional;

public class ConstructorDependencyDetector<T> extends DependencyDetector<T> {
  public ConstructorDependencyDetector(Class<T> clazz) {
    super(clazz);
  }

  @Override
  public Optional<List<Dependency>> detectImmediateDependencies() {
    Optional<Constructor<?>> injectableConstructorOptional = getInjectableConstructor();

    if (!injectableConstructorOptional.isPresent()) {
      return Optional.empty();
    }

    List<Dependency> dependencies = processParameters(injectableConstructorOptional.get());

    return Optional.of(dependencies);
  }

  private Optional<Constructor<?>> getInjectableConstructor() {
    Constructor<?>[] constructors = clazz.getConstructors();

    if (constructors.length != 1) {
      return Optional.empty();
    }

    Constructor<?> targetConstructor = constructors[0];

    if (targetConstructor.isAnnotationPresent(Inject.class)) {
      return Optional.of(targetConstructor);
    }

    return Optional.empty();
  }

  private List<Dependency> processParameters(Constructor<?> constructor) {
    return null;
  }

  @Override
  protected void setupInjector() {
    // TODO: Create ConstructorDependencyInjector
  }
}
