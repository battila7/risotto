package io.risotto.exception;

import io.risotto.binding.scope.Scope;

/**
 * {@code ScopeInstantiationException} is thrown when a specific scope could not been instantiated
 * for a binding.
 */
public class ScopeInstantiationException extends RuntimeException {
  private final Class<? extends Scope> scopeClass;

  /**
   * Constructs a new instance with the causing {@code Scope} class and exception.
   * @param scopeClass the scope {@code Risotto} failed to instantiate
   * @param e the causing exception
   */
  public ScopeInstantiationException(Class<? extends Scope> scopeClass, Exception e) {
    super("Could not instantiate scope.", e);

    this.scopeClass = scopeClass;
  }

  /**
   * Gets the causing {@code Scope} class.
   * @return the scope class
   */
  public Class<? extends Scope> getScopeClass() {
    return scopeClass;
  }
}
