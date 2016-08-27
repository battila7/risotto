package io.risotto.instantiation;

import io.risotto.annotations.Clone;
import io.risotto.exception.PrototypeCloneException;
import io.risotto.reflection.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * Cloner class that utilizes a copy constructor to produce clones. The copy constructor must be a
 * public constructor of the class with the {@link Clone} annotation present. The constructor may
 * only have a single parameter with the same type of the object itself.
 * @param <T> the type of the object to be cloned
 */
class ConstructorPrototypeCloner<T> extends PrototypeCloner<T> {
  private final Constructor<T> copyConstructor;

  /**
   * Constructs a new instance that will be used to clone instances of the specified class.
   * @param cloneableClass the class of the object to be cloned
   */
  public ConstructorPrototypeCloner(Class<? extends T> cloneableClass) {
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

      if (constructor != null) {
        constructor.setAccessible(true);
      }

      return (Constructor<T>) constructor;
    } catch (SecurityException e) {
      return null;
    }
  }
}
