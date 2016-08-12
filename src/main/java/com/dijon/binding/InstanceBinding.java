package com.dijon.binding;

import com.dijon.instantiation.NoOpInstantiator;

public class InstanceBinding<T> extends InstantiatableBinding<T> {
  public <K extends T> InstanceBinding(Binding<T> binding, K instance) {
    super(binding);

    instantiator = new NoOpInstantiator<>(instance);
  }

  public T getInstance() throws Exception {
    return instantiator.getInstance();
  }
}
