package com.dijon.binding;

import com.dijon.instantiation.InstantiatorFactory;
import com.dijon.instantiation.NoOpInstantiator;

public class InstanceBinding<T> extends InstantiatableBinding<T> {
  public <I extends T> InstanceBinding(Binding<T> binding, I instance) {
    super(binding);

    instantiator =
        InstantiatorFactory.decorateWithDefaultInstantiator(new NoOpInstantiator<>(instance));
  }
}
