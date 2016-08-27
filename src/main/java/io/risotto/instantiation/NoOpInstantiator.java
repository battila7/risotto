package io.risotto.instantiation;

import io.risotto.binding.InstanceBinding;
import io.risotto.dependency.Dependency;

import java.util.Collections;
import java.util.List;

/**
 * No operation instantiator is a fake instantiator that does not actually crete instances but wraps
 * an existing instance. This is the default instantiator for {@link InstanceBinding}s.
 * @param <T> the type of the wrapped instance
 */
public class NoOpInstantiator<T> implements Instantiator<T> {
  private final T instance;

  /**
   * Constructs a new instance storing the specified instance.
   * @param instance the instance to wrap
   */
  public NoOpInstantiator(T instance) {
    this.instance = instance;
  }

  @Override
  public T getInstance() {
    return instance;
  }

  @Override
  public Instantiator<T> getBaseInstantiator() {
    return this;
  }

  @Override
  public List<Dependency<?>> getImmediateDependencies() {
    return Collections.emptyList();
  }

  @Override
  @SuppressWarnings("unchecked")
  public Class<T> getInstantiatedClass() {
    return (Class<T>) instance.getClass();
  }
}
