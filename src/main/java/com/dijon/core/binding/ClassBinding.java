package com.dijon.core.binding;

import com.dijon.core.dependency.Dependency;

import java.util.List;

public class ClassBinding<T> extends InstantiatableBinding<T> {
  public T getInstance() {
    return null;
  }

  public List<Dependency> getDependencies() {
    return null;
  }

  public boolean canResolve(Dependency<T> dependency) {
    return false;
  }
}
