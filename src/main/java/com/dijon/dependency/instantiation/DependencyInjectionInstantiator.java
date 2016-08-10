package com.dijon.dependency.instantiation;

public class DependencyInjectionInstantiator<T> implements Instantiator<T> {
  private final Class<T> clazz;

  public DependencyInjectionInstantiator(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public T getInstance() {
    return null;
  }

  @Override
  public void instantiate() {

  }

  @Override
  public Instantiator<T> getBaseInstantiator() {
    return this;
  }
}
