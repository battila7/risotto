package com.dijon.dependency;

import com.dijon.binding.InstantiatableBinding;

public class Dependency<T> {
  private final Class<T> clazz;

  private InstantiatableBinding<?> resolvingBinding;

  public Dependency(Class<T> clazz) {
    this.clazz = clazz;
  }

  public Class<T> getBoundedClass() {
    return clazz;
  }

  public InstantiatableBinding<?> getResolvingBinding() {
    return resolvingBinding;
  }

  public void setResolvingBinding(InstantiatableBinding<?> resolvingBinding) {
    this.resolvingBinding = resolvingBinding;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Dependency<?> that = (Dependency<?>) o;

    return clazz.equals(that.clazz);
  }

  @Override
  public int hashCode() {
    return clazz.hashCode();
  }
}
