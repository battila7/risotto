package io.risotto.binding.scope;

import io.risotto.Container;
import io.risotto.dependency.Dependency;

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
