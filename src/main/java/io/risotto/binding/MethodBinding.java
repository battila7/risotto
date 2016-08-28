package io.risotto.binding;

import io.risotto.Container;
import io.risotto.instantiation.InstantiatorFactory;
import io.risotto.instantiation.MethodInstantiator;

import java.lang.reflect.Method;

/**
 * Binding that can be associated with <i>binding supplier</i> methods. Primarily used by
 * {@link io.risotto.configurator.BindingConfigurator}.
 * @param <T> the bound type
 */
public class MethodBinding<T> extends InstantiatableBinding<T> {
  /**
   * Constructs a new binding wrapping the passed binding and using the specified
   * container and method.
   * @param binding the binding to be wrapped
   * @param container the container that declares the binding supplier method
   * @param method the binding supplier method
   * @throws NullPointerException if a parameter is {@code null}
   */
  public MethodBinding(Binding<T> binding, Container container, Method method) {
    super(binding);

    if ((container == null) || (method == null)) {
      throw new NullPointerException("The container and the method must not be null!");
    }

    @SuppressWarnings("unchecked")
    Class<T> returnType = (Class<T>)method.getReturnType();

    this.instantiator = InstantiatorFactory.decorateWithDefaultInstantiator(
            new MethodInstantiator<>(returnType, container, method));
  }
}
