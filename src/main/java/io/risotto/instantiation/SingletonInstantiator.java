package io.risotto.instantiation;

/**
 * {@code SingletonInstantiator} caches an instance and returns that on every request. It's very
 * frequent that only one instance of a class should be used every where - that's the problem the
 * singleton pattern tries to solve. However making lots of classes singletons is considered an
 * anti-pattern.
 * <p>
 * In such situations {@code SingletionInstantiator} can be used because it ensures that only one
 * instance of a specific class exists in a container.
 * @param <T> the type of the cached instance
 */
public class SingletonInstantiator<T> extends InstantiatorDecorator<T> {
  private T instance;

  /**
   * Constructs a new instance decorating the specified instantiator. The only instance of the
   * cached class will be requested from the decorated instantiator.
   * @param decoratedInstantiator the instantiator to decorate
   */
  public SingletonInstantiator(Instantiator<T> decoratedInstantiator) {
    super(decoratedInstantiator);

    instance = null;
  }

  /**
   * Gets the instance cached in the instantiator. This will always be the same. This cached
   * instance is lazily created on the first request.
   * @return the cached instance
   */
  @Override
  public T getInstance() {
    if (instance == null) {
      instance = super.getInstance();
    }

    return instance;
  }
}
