package io.risotto.binding;

import io.risotto.instantiation.InstantiatorFactory;
import io.risotto.instantiation.NoOpInstantiator;

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
