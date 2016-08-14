package com.dijon.binding;

import com.dijon.instantiation.InstantiatorFactory;
import com.dijon.instantiation.NoOpInstantiator;

public class InstanceBinding<T> extends InstantiatableBinding<T> {
  public <I extends T> InstanceBinding(Binding<T> binding, I instance) {
    super(binding);

    if (instance == null) {
      throw new NullPointerException("The instance must not be null!");
    }

    instantiator =
        InstantiatorFactory.decorateWithDefaultInstantiator(new NoOpInstantiator<>(instance));
  }
}
