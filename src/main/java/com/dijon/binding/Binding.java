package com.dijon.binding;

import com.dijon.dependency.Dependency;

import java.util.Objects;

public abstract class Binding<T> {
  protected final Class<T> boundedClass;

  public Binding(Class<T> boundedClass) {
    this.boundedClass = Objects.requireNonNull(boundedClass, "The bounded class must not be null!");
  }

  public abstract boolean canResolve(Dependency<?> dependency);

  public Class<T> getBoundedClass() {
    return boundedClass;
  }
}
