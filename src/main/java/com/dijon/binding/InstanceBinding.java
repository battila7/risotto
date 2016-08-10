package com.dijon.binding;

import com.dijon.dependency.Dependency;
import com.dijon.dependency.instantiation.NoOpInstantiator;

import java.util.Collections;
import java.util.List;

public class InstanceBinding<T> extends InstantiatableBinding<T> {
  public <K extends T> InstanceBinding(Binding<T> binding, K instance) {
    super(binding);

    instantiator = new NoOpInstantiator<>(instance);
  }

  public T getInstance() {
    return null;
  }
}
