package io.risotto.instantiation;

import io.risotto.annotations.Clone;
import io.risotto.exception.PrototypeCloneException;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodPrototypeCloner<T> extends PrototypeCloner<T> {
  private final Method cloneMethod;

  public MethodPrototypeCloner(Class<T> cloneableClass) {
    super(cloneableClass);

    this.cloneMethod = detectCloneMethod();
  }

  @Override
  public boolean canClone() {
    return cloneMethod != null;
  }

  @Override
  public T createClone(T prototype) {
    if (!canClone()) {
      throw new PrototypeCloneException();
    }

    try {
      return cloneableClass.cast(cloneMethod.invoke(prototype));
    } catch (Exception e) {
      throw new PrototypeCloneException(e);
    }
  }

  private Method detectCloneMethod() {
    Method[] methods = cloneableClass.getDeclaredMethods();

    return Arrays.stream(methods)
        .filter(m -> m.isAnnotationPresent(Clone.class))
        .filter(m -> m.getParameterCount() == 0)
        .filter(m -> m.getReturnType().equals(Object.class))
        .findAny().orElse(null);
  }
}
