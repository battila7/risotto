package com.dijon.core.binding;

public abstract class ComposableBinding<T> implements Binding<T> {
  public InstantiatableBinding<T> toClass(Class<? extends T> clazz) {
    return null;
  }

  public <K extends T> InstantiatableBinding<T> toInstance(K instance) {
    return null;
  }
}
