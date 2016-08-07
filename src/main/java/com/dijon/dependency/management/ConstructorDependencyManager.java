package com.dijon.dependency.management;

public class ConstructorDependencyManager<T> implements DependencyManager<T> {
  private final Class<T> clazz;

  public ConstructorDependencyManager(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public DependencyDetector<T> getDetector() {
    return new ConstructorDependencyDetector<T>(clazz);
  }

  @Override
  public DependencyInjector<T> getInjector() {
    return new ConstructorDependencyInjector<T>(clazz);
  }
}
