package io.risotto.instantiation;


import io.risotto.exception.PrototypeCloneException;

import java.util.Arrays;
import java.util.List;

/**
 * This instantiator takes an instance and produces clones using the original instance as a
 * prototype. Cloning is performed using a copy constructor or a clone method. It's like a mixture
 * of {@code SingletionInstantiator} and {@code InstanceInstantiator}, it caches a prototype
 * instance but produces a new cloned instance on every request.
 * @param <T> the type of the object to be instantiated
 */
public class PrototypeInstantiator<T> extends InstantiatorDecorator<T> {
  private final PrototypeCloner<T> prototypeCloner;

  private T prototype;

  /**
   * Constructs a new instance decorating the specified instantiator. The prototype instance will be
   * requested from the decorated instantiator.
   * @param decoratedInstantiator the instantiator to decorate
   */
  public PrototypeInstantiator(Instantiator<T> decoratedInstantiator) {
    super(decoratedInstantiator);

    this.prototype = null;

    this.prototypeCloner = detectCloner();
  }

  @Override
  public T getInstance() {
    return obtainClone();
  }

  private T obtainClone() {
    if (prototypeCloner == null) {
      throw new PrototypeCloneException();
    }

    if (prototype == null) {
      prototype = super.getInstance();
    }

    return prototypeCloner.createClone(prototype);
  }

  private PrototypeCloner<T> detectCloner() {
    for (PrototypeCloner<T> cloner : getClonerList()) {
      if (cloner.canClone()) {
        return cloner;
      }
    }

    return null;
  }

  private List<PrototypeCloner<T>> getClonerList() {
    return Arrays.asList(new MethodPrototypeCloner<>(getInstantiatedClass()),
        new ConstructorPrototypeCloner<>(getInstantiatedClass()));
  }
}
