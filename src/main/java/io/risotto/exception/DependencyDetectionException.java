package io.risotto.exception;

/**
 * Arises when dependency detection fails on a specific class. This means that none of the
 * dependency detection strategies could return a valid list of dependencies for the specific
 * class.
 */
public class DependencyDetectionException extends RuntimeException {
  private static final String MESSAGE = "Dependency detection failed for class: ";

  private final Class<?> clazz;

  /**
   * Constructs a new instance with the class on which dependency detection failed.
   * @param clazz the erroneous class
   */
  public DependencyDetectionException(Class<?> clazz) {
    super(MESSAGE + clazz.toString());

    this.clazz = clazz;
  }

  /**
   * Gets the class on which dependency detection failed.
   * @return the erroneous class
   */
  public Class<?> getCausingClass() {
    return clazz;
  }
}
