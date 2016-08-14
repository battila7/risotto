package com.dijon.binding;

import com.dijon.dependency.Dependency;

public abstract class Binding<T> {
  protected final Class<T> boundedClass;

  public Binding(Class<T> boundedClass) {
    this.boundedClass = boundedClass;
  }

  public abstract boolean canResolve(Dependency<?> dependency);

  public Class<T> getBoundedClass() {
    return boundedClass;
  }
}
