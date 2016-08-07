package com.dijon.binding;

import com.dijon.dependency.management.DependencyInjector;

public class BindingHolder<T> {
  private final Binding<T> binding;

  private DependencyInjector<T> dependencyInjector;

  public BindingHolder(Binding<T> binding) {
    this.binding = binding;
  }

  public Binding<T> getBinding() {
    return binding;
  }

  public DependencyInjector<T> getDependencyInjector() {
    return dependencyInjector;
  }

  public void setDependencyInjector(DependencyInjector<T> dependencyInjector) {
    this.dependencyInjector = dependencyInjector;
  }
}
