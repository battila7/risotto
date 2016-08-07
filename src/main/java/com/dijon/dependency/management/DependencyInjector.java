package com.dijon.dependency.management;

public abstract class DependencyInjector<T> {
  protected final Class<T> clazz;

  public DependencyInjector(Class<T> clazz) {
    this.clazz = clazz;
  }
}
