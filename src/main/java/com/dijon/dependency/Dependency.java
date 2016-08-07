package com.dijon.dependency;

import com.dijon.dependency.management.DependencyInjector;

public class Dependency<T> {
  private final Class<T> clazz;

  private DependencyInjector<? extends T> dependencyInjector;

  public Dependency(Class<T> clazz) {
    this.clazz = clazz;
  }

  public DependencyInjector<? extends T> getDependencyInjector() {
    return dependencyInjector;
  }

  public void setDependencyInjector(DependencyInjector<? extends T> dependencyInjector) {
    this.dependencyInjector = dependencyInjector;
  }

  public Class<T> getBoundedClass() {
    return clazz;
  }
}
