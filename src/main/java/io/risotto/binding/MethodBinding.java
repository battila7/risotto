package io.risotto.binding;

import io.risotto.Container;
import io.risotto.instantiation.InstantiatorFactory;
import io.risotto.instantiation.MethodInstantiator;

import java.lang.reflect.Method;

public class MethodBinding<T> extends InstantiatableBinding<T> {
  @SuppressWarnings("unchecked")
  public MethodBinding(Binding<T> binding, Container container, Method method) {
    super(binding);

    if (method == null) {
      throw new NullPointerException("The method must not be null!");
    }

    this.instantiator =
        InstantiatorFactory.decorateWithDefaultInstantiator(
            new MethodInstantiator<>((Class<T>) method.getReturnType(), container, method));
  }
}
