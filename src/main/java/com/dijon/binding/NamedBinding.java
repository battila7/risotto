package com.dijon.binding;

import com.dijon.dependency.Dependency;

public class NamedBinding<T> extends ComposableBinding<T> {
  private final String name;

  public NamedBinding(Binding<T> binding, String name) {
    super(binding);

    this.name = name;
  }

  public boolean canResolve(Dependency<?> dependency) {
    return false;
  }
}
