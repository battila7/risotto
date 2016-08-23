package io.risotto.binding.scope;

import io.risotto.Container;
import io.risotto.dependency.Dependency;

/**
 * {@code PrivateScope} means "origin-only" scoping. Bindings are not visible for {@code
 * getInstance} requests neither for ancestor containers and are not imported to any of the
 * descendant containers.
 */
public class PrivateScope extends Scope {
  @Override
  public boolean isImportAllowedTo(Container targetContainer) {
    return false;
  }

  @Override
  public boolean isVisibleTo(Dependency<?> dependency) {
    return dependency.getOrigin() == origin;
  }
}