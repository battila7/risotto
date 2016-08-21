package com.dijon.exception;

import com.dijon.binding.scope.Scope;

public class ScopeInstantiationException extends RuntimeException {
  private final Class<? extends Scope> scopeClass;

  public ScopeInstantiationException(Class<? extends Scope> scopeClass, Exception e) {
    super("Could not instantiate scope.", e);

    this.scopeClass = scopeClass;
  }

  public Class<? extends Scope> getScopeClass() {
    return scopeClass;
  }
}
