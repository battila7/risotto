package io.risotto.instantiation;


public class PrototypeInstantiator<T> extends InstantiatorDecorator<T> {
  private T prototype;

  /**
   * Constructs a new instance decorating the specified instantiator. The prototype instance will be
   * requested from the decorated instantiator.
   * @param decoratedInstantiator the instantiator to decorate
   */
  public PrototypeInstantiator(Instantiator<T> decoratedInstantiator) {
    super(decoratedInstantiator);

    prototype = null;
  }

  @Override
  public T getInstance() {
    if (prototype == null) {
      prototype = super.getInstance();
    }

    return null;
  }
}
