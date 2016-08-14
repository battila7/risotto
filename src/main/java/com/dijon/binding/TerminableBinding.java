package com.dijon.binding;

public abstract class TerminableBinding<T> extends Binding<T> {
  public TerminableBinding(Class<T> clazz) {
    super(clazz);
  }

  public InstantiatableBinding<T> toClass(Class<? extends T> targetClass) {
    return new ClassBinding<>(this, targetClass);
  }

  public <K extends T> InstantiatableBinding<T> toInstance(K instance) {
    return new InstanceBinding<>(this, instance);
  }
}
