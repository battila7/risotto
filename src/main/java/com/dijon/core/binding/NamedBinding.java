package com.dijon.core.binding;

import com.dijon.core.dependency.Dependency;

public class NamedBinding<T> extends ComposableBinding<T> {
  private final String name;

  public NamedBinding(Binding<T> binding, String name) {
    super(binding);

    this.name = name;
  }

  public boolean canResolve(Dependency<T> dependency) {
    return false;
  }
}
