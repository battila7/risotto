package com.dijon.core.binding;

import com.dijon.core.dependency.Dependency;

public abstract class AbstractBinding<T> {
  public void toClass(Class<? extends T> clazz) {

  }

  public <I extends T> void toInstance(I instance) {

  }

  public boolean canResolve(Dependency<T> dependency) {
    return false;
  }
}
