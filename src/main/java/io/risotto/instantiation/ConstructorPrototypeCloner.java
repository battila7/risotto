package io.risotto.instantiation;

import io.risotto.annotations.Clone;
import io.risotto.exception.PrototypeCloneException;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Optional;
import reflection.ReflectionUtils;

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

  @SuppressWarnings("unchecked")
  private Constructor<T> detectCopyConstructor() {
    try {
      Constructor<?> constructor =
          Arrays.stream(cloneableClass.getDeclaredConstructors())
          .filter(c -> c.isAnnotationPresent(Clone.class))
          .filter(c -> c.getParameterCount() == 1)
          .filter(c -> c.getParameterTypes()[0] == cloneableClass)
          .filter(ReflectionUtils::isPublicNotStaticNotFinal)
          .findAny().orElse(null);

      return (Constructor<T>)constructor;
    } catch (SecurityException e) {
      return null;
    }
  }
}
