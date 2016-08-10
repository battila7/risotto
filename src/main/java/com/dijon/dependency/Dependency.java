package com.dijon.dependency;

import com.dijon.binding.InstantiatableBinding;

public class Dependency<T> {
  private final Class<T> clazz;

  private InstantiatableBinding<? extends T> resolvingBinding;

  public Dependency(Class<T> clazz) {
    this.clazz = clazz;
  }

  public Class<T> getBoundedClass() {
    return clazz;
  }

  public InstantiatableBinding<? extends T> getResolvingBinding() {
    return resolvingBinding;
  }

  public void setResolvingBinding(InstantiatableBinding<? extends T> resolvingBinding) {
    this.resolvingBinding = resolvingBinding;
  }
}
