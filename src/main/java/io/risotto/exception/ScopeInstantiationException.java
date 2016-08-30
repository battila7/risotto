package io.risotto.exception;

import io.risotto.binding.Binding;
import io.risotto.binding.scope.Scope;

/**
 * {@code ScopeInstantiationException} is thrown when a specific scope could not been instantiated
 * for a binding.
 */
public class ScopeInstantiationException extends RuntimeException {
  private static final String MESSAGE = "Could not instantiate scope: ";

  private final Binding<?> binding;

  private final Class<? extends Scope> scopeClass;

  /**
   * Constructs a new instance with the causing {@code Scope} class and exception.
   * @param binding the binding the scope class was applied to
   * @param scopeClass the scope {@code Risotto} failed to instantiate
   * @param e the causing exception
   */
  public ScopeInstantiationException(Binding<?> binding, Class<? extends Scope> scopeClass,
                                     Exception e) {
    super(MESSAGE + binding.toString() + " " + scopeClass.toString(), e);

    this.binding = binding;

    this.scopeClass = scopeClass;
  }

  /**
   * Gets the binding the scope was applied to.
   * @return the binding
   */
  public Binding<?> getBinding() {
    return binding;
  }

  /**
   * Gets the causing {@code Scope} class.
   * @return the scope class
   */
  public Class<? extends Scope> getScopeClass() {
    return scopeClass;
  }
}
