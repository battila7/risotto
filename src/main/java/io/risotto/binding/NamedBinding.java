package io.risotto.binding;

import io.risotto.dependency.Dependency;
import io.risotto.dependency.NamedDependency;

import java.util.Objects;

/**
 * {@code NamedBinding} represents a binding that uses a name to resolve dependencies. There are no
 * constraints on the name used in the binding. {@code NamedBinding}s are used to resolve {@link
 * NamedDependency} objects.
 * @param <T> the bound type
 */
public class NamedBinding<T> extends TerminableBinding<T> {
  private final String name;

  /**
   * Constructs a new binding, binding to the specified class with the specified name.
   * @param boundClass the bound class
   * @param name the name of the binding
   */
  public NamedBinding(Class<T> boundClass, String name) {
    super(boundClass);

    this.name = Objects.requireNonNull(name, "The name must not be null!");
  }

  /**
   * Returns whether the binding can resolve the specified dependency. Returns {@code true} if the
   * class bound by this binding is a <b>subclass</b> of the class held in the dependency and the
   * name referred by the dependency is <b>equal to</b> the name associated with the binding.
   * @param dependency the dependency to resolve
   * @return whether the binding can resolve the dependency
   */
  public boolean canResolve(Dependency<?> dependency) {
    if (!(dependency instanceof NamedDependency)) {
      return false;
    }

    NamedDependency<?> namedDependency = (NamedDependency<?>) dependency;

    return namedDependency.getName().equals(name)
        && namedDependency.getBoundedClass().isAssignableFrom(boundedClass);
  }

  /**
   * Gets the name associated with the binding.
   * @return the associated name
   */
  public String getName() {
    return name;
  }
}
