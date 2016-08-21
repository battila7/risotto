package io.risotto.binding.scope;

import io.risotto.Container;
import io.risotto.dependency.Dependency;

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
