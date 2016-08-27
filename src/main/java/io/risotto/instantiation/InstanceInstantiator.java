package io.risotto.instantiation;

import io.risotto.binding.InstanceBinding;

/**
 * {@code InstanceInstantiator} creates a brand new object on every request. In contrast to the
 * {@link SingletonInstantiator}, a new instance is injected into every reference to the type
 * bounded to this instantiator.
 *
 * <b>Note</b> that this instantiator cannot be used in conjunction with {@link InstanceBinding}s.
 * @param <T> the type of the object to be instantiated
 */
public class InstanceInstantiator<T> extends InstantiatorDecorator<T> {
  /**
   * Constructs a new instance decorating the specified instantiator. Subsequent instances will be
   * requested from the decorated instantiator.
   * @param decoratedInstantiator the instantiator to decorate
   */
  public InstanceInstantiator(Instantiator<T> decoratedInstantiator) {
    super(decoratedInstantiator);
  }

  /**
   * Gets a new instance on every request.
   * @return a new instance
   */
  @Override
  public T getInstance() {
    return super.getInstance();
  }
}
