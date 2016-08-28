package io.risotto.binding;

import io.risotto.Container;
import io.risotto.instantiation.InstantiatorFactory;
import io.risotto.instantiation.MethodInstantiator;

import java.lang.reflect.Method;

public class MethodBinding<T> extends InstantiatableBinding<T> {
  public MethodBinding(Binding<T> binding, Container container, Method method) {
    super(binding);

    if (method == null) {
      throw new NullPointerException("The method must not be null!");
    }

    @SuppressWarnings("unchecked")
    Class<T> returnType = (Class<T>)method.getReturnType();

    this.instantiator = InstantiatorFactory.decorateWithDefaultInstantiator(
            new MethodInstantiator<>(returnType, container, method));
  }
}
