package com.dijon.binding;

import com.dijon.dependency.Dependency;

import java.util.Collections;
import java.util.List;

public class InstanceBinding<T> extends InstantiatableBinding<T> {
  private final T instance;

  public <K extends T> InstanceBinding(Binding<T> binding, K instance) {
    super(binding);

    this.instance = instance;
  }

  public T getInstance() {
    return null;
  }
}
