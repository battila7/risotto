package com.dijon.binding;

public abstract class ComposableBinding<T> implements Binding<T> {
  protected final Binding<T> binding;

  public ComposableBinding(Binding<T> binding) {
    this.binding = binding;
  }

  public InstantiatableBinding<T> toClass(Class<? extends T> clazz) {
    return new ClassBinding<>(this, clazz);
  }

  public <K extends T> InstantiatableBinding<T> toInstance(K instance) {
    return new InstanceBinding<>(this, instance);
  }

  public Class<T> getBoundedClass() {
    return binding.getBoundedClass();
  }
}