package io.risotto.binding;

import io.risotto.instantiation.InstantiatorFactory;
import io.risotto.instantiation.NoOpInstantiator;

/**
 * This binding binds an actual instance to its bound class. The class of the instance held in the
 * binding must be a subclass of the binding's bound class. This instance is then used to resolve
 * dependencies with appropriate types.
 * @param <T> the bound type
 */
public class InstanceBinding<T> extends InstantiatableBinding<T> {
  /**
   * Constructs a new {@code InstanceBinding} holding the specified instance and wrapping the
   * specified binding.
   * @param binding the binding to wrap
   * @param instance the associated instance
   * @param <I> the type of the instance, must be a subtype of the bound type
   */
  public <I extends T> InstanceBinding(Binding<T> binding, I instance) {
    super(binding);

    if (instance == null) {
      throw new NullPointerException("The instance must not be null!");
    }

    instantiator =
        InstantiatorFactory.decorateWithDefaultInstantiator(new NoOpInstantiator<>(instance));
  }
}
