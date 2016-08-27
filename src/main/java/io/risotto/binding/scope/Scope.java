package io.risotto.binding.scope;

import io.risotto.Container;
import io.risotto.binding.InstantiatableBinding;
import io.risotto.dependency.Dependency;

/**
 * With {@code Scope} the visibility and importation of the bindings can be fine-grained. Besides
 * the default {@code Scope} implementations, custom scopes can be added to bindings using the
 * {@link InstantiatableBinding#withScope(Class)} method.
 *
 * Dependencies and bindings (through {@code Scope} instances) have origin containers. Scopes define
 * whether a binding is visible to a dependency coming from a specific origin container. Furthermore
 * scopes allow bindings to be imported to child containers so that child containers can reach the
 * bindings of their parent container.
 */
public abstract class Scope {
  /**
   * Enables the detection of requests that come from outside of the container tree and enables
   * scopes to alter the visibility of the underlying binding based on whether the request was made
   * by the library itself or by user code.
   * @see Container#getInstance(Class)
   */
  public static final Container GET_INSTANCE_REQUEST = null;

  protected Container origin;

  /**
   * Gets the origin container of the {@code Scope}. The origin container is the container in which
   * the scope object was created.
   * @return the origin container
   */
  public final Container getOrigin() {
    return origin;
  }

  /**
   * Sets the origin container.
   * @param origin the origin container of the scope
   */
  public final void setOrigin(Container origin) {
    this.origin = origin;
  }

  /**
   * Checks whether the scope allows importation from its origin container to the specified target
   * container.
   * @param targetContainer the container the import is requested into
   * @return whether the import is allowed or not
   */
  public abstract boolean isImportAllowedTo(Container targetContainer);

  /**
   * Checks whether the underlying binding is visible to the specified dependency.
   * @param dependency a resolvable dependency
   * @return whether the underlying binding is visible or not
   */
  public abstract boolean isVisibleTo(Dependency<?> dependency);
}
