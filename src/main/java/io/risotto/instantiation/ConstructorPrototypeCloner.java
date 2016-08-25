package io.risotto.instantiation;

import io.risotto.exception.PrototypeCloneException;

import java.lang.reflect.Constructor;

public class ConstructorPrototypeCloner<T> extends PrototypeCloner<T> {
  private final Constructor<T> copyConstructor;

  public ConstructorPrototypeCloner(Class<T> cloneableClass) {
    super(cloneableClass);

    copyConstructor = detectCopyConstructor();
  }

  @Override
  public boolean canClone() {
    return copyConstructor != null;
  }

  @Override
  public T createClone(T prototype) {
    if (!canClone()) {
      throw new PrototypeCloneException();
    }

    try {
      return copyConstructor.newInstance(prototype);
    } catch (Exception e) {
      throw new PrototypeCloneException(e);
    }
  }

  private Constructor<T> detectCopyConstructor() {
    try {
      return cloneableClass.getDeclaredConstructor(cloneableClass);
    } catch (NoSuchMethodException | SecurityException e) {
      return null;
    }
  }
}
