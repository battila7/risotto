package com.dijon.dependency;

import java.util.List;

public abstract class DependencyInjector<T> {
  protected final Class<T> clazz;

  protected final List<Dependency<?>> dependencies;

  public DependencyInjector(Class<T> clazz, List<Dependency<?>> dependencies) {
    this.clazz = clazz;

    this.dependencies = dependencies;
  }

  public abstract T createInstance();
}
