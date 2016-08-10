package com.dijon.instantiation;

import com.dijon.dependency.Dependency;

import java.util.Collections;
import java.util.List;

public class NoOpInstantiator<T> implements Instantiator<T> {
  private final T instance;

  public NoOpInstantiator(T instance) {
    this.instance = instance;
  }

  @Override
  public T getInstance() {
    return instance;
  }

  @Override
  public void instantiate() {
    /*
     *  Do nothing.
     */
  }

  @Override
  public Instantiator<T> getBaseInstantiator() {
    return this;
  }

  @Override
  public List<Dependency<?>> getImmediateDependencies() {
    return Collections.emptyList();
  }
}
