package com.dijon.core.binding;

import com.dijon.core.dependency.Dependency;

import java.util.List;

public abstract class InstantiatableBinding<T> implements Binding<T> {
  public abstract T getInstance();

  public abstract List<Dependency> getDependencies();
}
