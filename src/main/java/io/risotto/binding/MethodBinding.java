package io.risotto.binding;

import java.lang.reflect.Method;

public class MethodBinding<T> extends InstantiatableBinding<T> {
  private final Method method;

  public MethodBinding(Binding<T> binding, Method method) {
    super(binding);

    this.method = method;
  }
}
