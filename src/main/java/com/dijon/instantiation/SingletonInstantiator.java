package com.dijon.instantiation;

public class SingletonInstantiator<T> extends InstantiatorDecorator<T> {
  private T instance;

  public SingletonInstantiator(Instantiator<T> decoratedInstantiator) {
    super(decoratedInstantiator);

    instance = null;
  }

  @Override
  public T getInstance() {
    if (instance == null) {
      instance = super.getInstance();
    }

    return instance;
  }
}
