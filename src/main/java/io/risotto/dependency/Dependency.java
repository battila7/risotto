package io.risotto.dependency;

import io.risotto.Container;
import io.risotto.annotations.Inject;
import io.risotto.binding.BasicBinding;
import io.risotto.binding.InstantiatableBinding;

/**
 * Represents a dependency on a specific type. Dependencies are detected by detectors and actually
 * produced by a dependency processor chain.
 *
 * Dependency detection is driven by the {@link Inject} annotation. Constructors, setter methods and
 * fields with the {@code Inject} annotation can produce new dependencies. These are the immediate
 * dependencies of a class. Only immediate dependencies are detected because eventually an immediate
 * dependency of a specific class might be a second or third-level dependency of another class.
 * <p>
 * When configuring containers, all the dependencies of the classes are collected and placed in a
 * set (that means, there are no duplicates). The dependencies in this set can be resolved in an
 * undefined order using the bindings created in or imported into the container.
 * <p>
 * A specific instance of {@code Dependency} can only be resolved by {@link BasicBinding} objects
 * binding a child type of the type represented by the {@code Dependency}.
 * @param <T> the type represented by the dependency
 */
public class Dependency<T> {
  private final Class<T> boundedClass;

  private Container origin;

  private InstantiatableBinding<?> resolvingBinding;

  /**
   * Creates a new instance that represents a dependency on the specified class.
   * @param boundedClass the class another class is dependant on
   */
  public Dependency(Class<T> boundedClass) {
    this.boundedClass = boundedClass;
  }

  /**
   * Gets the bounded class of the dependency. That's the class another class is dependant on.
   * @return the bounded class
   */
  public Class<T> getBoundedClass() {
    return boundedClass;
  }

  /**
   * Gets the container in which the dependency arisen.
   * @return the origin container
   */
  public Container getOrigin() {
    return origin;
  }

  /**
   * Sets the origin container of the dependency.
   * @param origin the origin container
   */
  public void setOrigin(Container origin) {
    this.origin = origin;
  }

  /**
   * Gets the binding that is able to resolve the dependency.
   * @return the binding that can resolve the dependency
   */
  public InstantiatableBinding<?> getResolvingBinding() {
    return resolvingBinding;
  }

  /**
   * Sets the binding that is able to resolve the dependency. This binding is found when dependency
   * resolution is performed.
   * @param resolvingBinding the binding that can resolve the dependency
   */
  public void setResolvingBinding(InstantiatableBinding<?> resolvingBinding) {
    this.resolvingBinding = resolvingBinding;
  }

  @Override
  public String toString() {
    return "Dependency{" +
        "boundedClass=" + boundedClass +
        ", origin=" + origin +
        ", resolvingBinding=" + resolvingBinding +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Dependency<?> that = (Dependency<?>) o;

    if (!boundedClass.equals(that.boundedClass)) {
      return false;
    }
    if (origin != null ? !origin.equals(that.origin) : that.origin != null) {
      return false;
    }
    return resolvingBinding != null ? resolvingBinding.equals(that.resolvingBinding)
        : that.resolvingBinding == null;

  }

  @Override
  public int hashCode() {
    int result = boundedClass.hashCode();
    result = 31 * result + (origin != null ? origin.hashCode() : 0);
    result = 31 * result + (resolvingBinding != null ? resolvingBinding.hashCode() : 0);
    return result;
  }
}
