package com.dijon.binding;

import com.dijon.instantiation.DependencyInjectionInstantiator;

public class ClassBinding<T> extends InstantiatableBinding<T> {
  private final Class<? extends T> clazz;

  public ClassBinding(Binding<T> binding, Class<? extends T> clazz) {
    super(binding);

    this.clazz = clazz;

    this.instantiator = new DependencyInjectionInstantiator<>(clazz);
  }

  public T getInstance() {
    return null;
  }
}
