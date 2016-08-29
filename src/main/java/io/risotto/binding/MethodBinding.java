package io.risotto.binding;

import io.risotto.Container;
import io.risotto.instantiation.InstantiatorFactory;
import io.risotto.instantiation.MethodInstantiator;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Binding that can be associated with <i>binding supplier</i> methods. Primarily used by
 * {@link io.risotto.configurator.BindingConfigurator}.
 * @param <T> the bound type
 */
public class MethodBinding<T> extends InstantiatableBinding<T> {
  private final Container container;

  private final Method method;

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

    this.container = Objects.requireNonNull(container);

    this.method = Objects.requireNonNull(method);

    @SuppressWarnings("unchecked")
    Class<T> returnType = (Class<T>)method.getReturnType();

    this.instantiator = InstantiatorFactory.decorateWithDefaultInstantiator(
            new MethodInstantiator<>(returnType, container, method));
  }

  @Override
  public String toString() {
    return "MethodBinding{" +
        "container=" + container +
        ", method=" + method +
        "} " + super.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    MethodBinding<?> that = (MethodBinding<?>) o;

    if (!container.equals(that.container)) {
      return false;
    }
    return method.equals(that.method);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + container.hashCode();
    result = 31 * result + method.hashCode();
    return result;
  }
}
