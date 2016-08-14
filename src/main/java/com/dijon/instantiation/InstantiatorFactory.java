package com.dijon.instantiation;

public class InstantiatorFactory {
  public static <T> Instantiator<T> decorateInstantiatorForMode(Instantiator<T> instantiator,
                                                                InstantiationMode mode) {
    Instantiator<T> base = instantiator.getBaseInstantiator();

    switch (mode) {
      case SINGLETON:
        return new SingletonInstantiator<>(base);
      case PROTOTYPE:
        return null;
      case INSTANCE:
        return null;
      default:
        return null;
    }
  }

  public static <T> Instantiator<T> decorateWithDefaultInstantiator(Instantiator<T> instantiator) {
    Instantiator<T> base = instantiator.getBaseInstantiator();

    return new SingletonInstantiator<>(base);
  }
}
