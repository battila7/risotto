package com.dijon.dependency;

import com.dijon.dependency.constructor.ConstructorDependencyDetector;
import com.dijon.dependency.field.FieldDependencyDetector;
import com.dijon.dependency.setter.SetterDependencyDetector;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class DependencyDetector<T> {
  protected final Class<T> clazz;

  protected DependencyInjector<T> dependencyInjector;

  public static <C> List<DependencyDetector<C>> createDetectors(Class<C> clazz) {
    return Arrays
        .asList(new ConstructorDependencyDetector<>(clazz), new SetterDependencyDetector<>(clazz),
            new FieldDependencyDetector<>(clazz));
  }

  public DependencyDetector(Class<T> clazz) {
    this.clazz = clazz;
  }

  public abstract Optional<List<Dependency<?>>> detectImmediateDependencies();

  public DependencyInjector<T> getInjector() {
    if (dependencyInjector == null) {
      throw new IllegalStateException(
          "Dependency detection is not yet performed or could not create injector!");
    }

    return this.dependencyInjector;
  }
}
