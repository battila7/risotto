package com.dijon.instantiation;

import com.dijon.dependency.Dependency;
import com.dijon.exception.DependencyDetectionException;

import java.util.List;

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
  public Instantiator<T> getBaseInstantiator() {
    return decoratedInstantiator.getBaseInstantiator();
  }

  @Override
  public List<Dependency<?>> getImmediateDependencies() {
    return decoratedInstantiator.getImmediateDependencies();
  }
}
