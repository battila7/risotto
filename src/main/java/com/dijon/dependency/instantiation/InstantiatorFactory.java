package com.dijon.dependency.instantiation;

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
}
