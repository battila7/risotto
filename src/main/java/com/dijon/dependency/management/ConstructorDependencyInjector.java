package com.dijon.dependency.management;

public class ConstructorDependencyInjector<T> extends DependencyInjector<T> {
  public ConstructorDependencyInjector(Class<T> clazz) {
    super(clazz);
  }
}
