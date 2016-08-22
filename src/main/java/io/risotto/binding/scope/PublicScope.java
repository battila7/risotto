package io.risotto.binding.scope;

import io.risotto.Container;
import io.risotto.dependency.Dependency;

/**
 * {@code PublicScope} is the widest and the <b>default</b> scope. Bindings with
 * public scope are visible to {@code getInstance} requests and to all ancestors
 * of their origin container. They are also imported to all descendant containers.
 */
public class PublicScope extends Scope {
  @Override
  public boolean isImportAllowedTo(Container targetContainer) {
    return true;
  }

  @Override
  public boolean isVisibleTo(Dependency<?> dependency) {
    return true;
  }
}
