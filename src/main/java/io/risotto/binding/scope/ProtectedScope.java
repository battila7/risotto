package io.risotto.binding.scope;

import io.risotto.Container;
import io.risotto.dependency.Dependency;

/**
 * {@code ProtectedScope} is a stricter version of {@link PublicScope} in terms of importation.
 * Bindings with protected scope are visible to {@code getInstance} requests and to all ancestors of
 * their origin container but on the other hand they are not imported to the descendant containers.
 */
public class ProtectedScope extends Scope {
  @Override
  public boolean isImportAllowedTo(Container targetContainer) {
    return false;
  }

  @Override
  public boolean isVisibleTo(Dependency<?> dependency) {
    return true;
  }
}
