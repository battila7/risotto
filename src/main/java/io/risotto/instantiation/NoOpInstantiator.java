package io.risotto.instantiation;

import io.risotto.binding.InstanceBinding;
import io.risotto.dependency.Dependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * No operation instantiator is a fake instantiator that does not actually crete instances but wraps
 * an existing instance. This is the default instantiator for {@link InstanceBinding}s.
 * @param <T> the type of the wrapped instance
 */
public class NoOpInstantiator<T> implements Instantiator<T> {
  private static final Logger logger = LoggerFactory.getLogger(NoOpInstantiator.class);

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
    logger.debug("Serving wrapped instance of {}", getInstantiatedClass());

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
