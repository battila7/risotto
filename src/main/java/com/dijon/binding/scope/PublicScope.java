package com.dijon.binding.scope;

import com.dijon.Container;
import com.dijon.dependency.Dependency;

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
