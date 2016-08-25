package io.risotto.instantiation;

import io.risotto.dependency.Dependency;

import java.util.List;

/**
 * Abstract decorator class to wrap instantiators with other instantiators. That way the exact
 * process of object creation can be altered. This is mostly used to cache previously created
 * instances (for example in {@link SingletonInstantiator}).
 */
public abstract class InstantiatorDecorator<T> implements Instantiator<T> {
  protected final Instantiator<T> decoratedInstantiator;

  /**
   * Constructs a new instance decorating the specified instantiator.
   * @param decoratedInstantiator the instantiator to decorate
   */
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

  @Override
  public Class<T> getTargetClass() {
    return decoratedInstantiator.getTargetClass();
  }
}
