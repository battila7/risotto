package com.dijon.instantiation;

import com.dijon.dependency.Dependency;

import java.util.List;

public class DependencyInjectionInstantiator<T> implements Instantiator<T> {
  private final Class<? extends T> clazz;

  public DependencyInjectionInstantiator(Class<? extends T> clazz) {
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

  @Override
  public List<Dependency<?>> getImmediateDependencies() {
    return null;
  }
}
