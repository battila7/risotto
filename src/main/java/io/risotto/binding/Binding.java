package io.risotto.binding;

import io.risotto.dependency.Dependency;

import java.util.Objects;

/**
 * Bindings are the objects that can resolve dependencies. Each binding has a bound type. Bindings
 * can resolve dependencies of their supertypes.
 * <p>
 * When there are multiple bindings to the same type, named or annotated bindings/dependencies could
 * be used to ensure that a specific binding is used to resolve a dependency. Otherwise it's
 * unpredictable which binding will be used to resolve the dependency.
 * @param <T> the bound type
 */
public abstract class Binding<T> {
  protected final Class<T> boundedClass;

  /**
   * Constructs a new binding, binding to the specified class
   * @param boundClass the bound class
   * @throws NullPointerException if the bounded class is {@code null}
   */
  public Binding(Class<T> boundClass) {
    this.boundedClass = Objects.requireNonNull(boundClass, "The bounded class must not be null!");
  }

  /**
   * Returns whether the binding can resolve the specified dependency.
   * @param dependency the dependency to resolve
   * @return whether the binding can resolve the dependency
   */
  public abstract boolean canResolve(Dependency<?> dependency);

  /**
   * Gets the bounded class.
   * @return the bounded class
   */
  public Class<T> getBoundedClass() {
    return boundedClass;
  }
}
