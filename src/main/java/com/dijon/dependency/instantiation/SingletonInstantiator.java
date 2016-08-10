package com.dijon.dependency.instantiation;

public class SingletonInstantiator<T> extends InstantiatorDecorator<T> {
  public SingletonInstantiator(Instantiator<T> decoratedInstantiator) {
    super(decoratedInstantiator);
  }


}
