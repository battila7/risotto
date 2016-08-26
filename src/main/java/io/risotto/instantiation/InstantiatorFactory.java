package io.risotto.instantiation;

import static io.risotto.instantiation.InstantiationMode.INSTANCE;

/**
 * Factory class that provides static methods to decorate instantiators with appropriate
 * decorator implementations. That way the concrete implementations can be abstracted away from
 * client code using the factory class.
 */
public final class InstantiatorFactory {
  /**
   * Returns a decorated instantiator using the specified base instantiator and the default decorator
   * associated with the value of the {@code mode} parameter.
   * @param instantiator the instantiator to decorate
   * @param mode the mode of which a decorator is requested
   * @param <T> the requested instance's type
   * @return the decorated instantiator
   */
  public static <T> Instantiator<T> decorateInstantiatorForMode(Instantiator<T> instantiator,
                                                                InstantiationMode mode) {
    Instantiator<T> base = instantiator.getBaseInstantiator();

    switch (mode) {
      case PROTOTYPE:
        return new PrototypeInstantiator<>(base);
      case INSTANCE:
        return new InstanceInstantiator<>(base);
      default:
        return new SingletonInstantiator<>(base);
    }
  }

  /**
   * Returns a decorated instantiator decorated with the default decorator implementation.
   * @param instantiator the instantiator to decorate
   * @param <T> the requested instance's type
   * @return the decorated instantiator
   */
  public static <T> Instantiator<T> decorateWithDefaultInstantiator(Instantiator<T> instantiator) {
    Instantiator<T> base = instantiator.getBaseInstantiator();

    return new SingletonInstantiator<>(base);
  }

  private InstantiatorFactory() {
    /*
     * Cannot be instantiated.
     */
  }
}
