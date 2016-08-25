package io.risotto.instantiation;


import io.risotto.exception.PrototypeCloneException;

import java.util.Arrays;
import java.util.List;

public class PrototypeInstantiator<T> extends InstantiatorDecorator<T> {
  private final Class<T> cloneableClass;

  private final PrototypeCloner<T> prototypeCloner;

  private T prototype;

  /**
   * Constructs a new instance decorating the specified instantiator. The prototype instance will be
   * requested from the decorated instantiator.
   * @param decoratedInstantiator the instantiator to decorate
   * @param cloneableClass the class of the prototype object
   */
  public PrototypeInstantiator(Instantiator<T> decoratedInstantiator, Class<T> cloneableClass) {
    super(decoratedInstantiator);

    this.prototype = null;

    this.cloneableClass = cloneableClass;

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
    return Arrays.asList(new MethodPrototypeCloner<>(cloneableClass),
        new ConstructorPrototypeCloner<>(cloneableClass));
  }
}
