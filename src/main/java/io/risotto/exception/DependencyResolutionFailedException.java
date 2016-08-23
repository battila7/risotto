package io.risotto.exception;

import io.risotto.dependency.Dependency;

/**
 * This kind of dependency is thrown when @code Risotto} was not able to find a binding that can
 * resolve a dependency.
 */
public class DependencyResolutionFailedException extends Exception {
  private static final String MESSAGE = "Dependency resolution failed for dependency: ";

  private final Dependency<?> dependency;

  /**
   * Constructs a new instance with the dependency the could not been resolved.
   * @param dependency the unresolvable dependency
   */
  public DependencyResolutionFailedException(Dependency<?> dependency) {
    super(MESSAGE + dependency.toString());

    this.dependency = dependency;
  }

  /**
   * Gets the dependency {@code Risotto} could not resolve.
   * @return the unresolvable dependency
   */
  public Dependency<?> getDependency() {
    return dependency;
  }
}
