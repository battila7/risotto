package com.dijon.instantiation;

public class SingletonInstantiator<T> extends InstantiatorDecorator<T> {
  public SingletonInstantiator(Instantiator<T> decoratedInstantiator) {
    super(decoratedInstantiator);
  }


}
