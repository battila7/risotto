package com.dijon.dependency.instantiation;

public abstract class InstantiatorDecorator<T> implements Instantiator<T> {
  protected final Instantiator<T> decoratedInstantiator;

  public InstantiatorDecorator(Instantiator<T> decoratedInstantiator) {
    this.decoratedInstantiator = decoratedInstantiator;
  }

  @Override
  public T getInstance() {
    return decoratedInstantiator.getInstance();
  }

  @Override
  public void instantiate() {
    decoratedInstantiator.instantiate();;
  }

  @Override
  public Instantiator<T> getBaseInstantiator() {
    return decoratedInstantiator.getBaseInstantiator();
  }
}
