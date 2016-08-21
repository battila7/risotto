package com.dijon.dependency;

import com.dijon.Container;
import com.dijon.binding.InstantiatableBinding;

public class Dependency<T> {
  private final Class<T> boundedClass;

  private Container origin;

  private InstantiatableBinding<?> resolvingBinding;

  public Dependency(Class<T> boundedClass) {
    this.boundedClass = boundedClass;
  }

  public Class<T> getBoundedClass() {
    return boundedClass;
  }

  public Container getOrigin() {
    return origin;
  }

  public void setOrigin(Container origin) {
    this.origin = origin;
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

    return boundedClass.equals(that.boundedClass);
  }

  @Override
  public int hashCode() {
    return boundedClass.hashCode();
  }
}
