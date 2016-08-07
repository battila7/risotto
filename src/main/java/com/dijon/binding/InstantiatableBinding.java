package com.dijon.binding;

import com.dijon.dependency.Dependency;

import java.util.List;

public abstract class InstantiatableBinding<T> implements Binding<T> {
  protected final Binding<T> binding;

  public InstantiatableBinding(Binding<T> binding) {
    this.binding = binding;
  }

  public abstract T getInstance();

  public boolean canResolve(Dependency<T> dependency) {
    return binding.canResolve(dependency);
  }

  public Class<T> getBoundedClass() {
    return binding.getBoundedClass();
  }
}
