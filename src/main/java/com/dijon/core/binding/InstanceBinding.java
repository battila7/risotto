package com.dijon.core.binding;

import com.dijon.core.dependency.Dependency;

import java.util.List;

public class InstanceBinding<T> extends InstantiatableBinding<T> {
  private final T instance;

  public <K extends T> InstanceBinding(Binding<T> binding, K instance) {
    super(binding);

    this.instance = instance;
  }

  public T getInstance() {
    return null;
  }

  public List<Dependency> getDependencies() {
    return null;
  }
}
