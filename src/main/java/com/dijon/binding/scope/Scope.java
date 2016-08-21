package com.dijon.binding.scope;

import com.dijon.Container;
import com.dijon.dependency.Dependency;

public abstract class Scope {
  public static final Container GET_INSTANCE_REQUEST = null;

  protected Container origin;

  public final Container getOrigin() {
    return origin;
  }

  public final void setOrigin(Container origin) {
    this.origin = origin;
  }

  public abstract boolean isImportAllowedTo(Container targetContainer);

  public abstract boolean isVisibleTo(Dependency<?> dependency);
}
