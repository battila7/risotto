package io.risotto.exception;

/**
 * Thrown when {@code Risotto} is not able to instantiate a class.
 */
public class InstantiationFailedException extends RuntimeException {
  private final Class<?> failingClass;

  /**
   * Constructs a new instance with the cause.
   * @param failingClass the {@code Class} Risotto failed to instantiate
   * @param cause the cause of the exception
   */
  public InstantiationFailedException(Class<?> failingClass, Throwable cause) {
    super(cause);

    this.failingClass = failingClass;
  }

  /**
   * Gets the class that could not been instantiated and is the cause of the exception.
   * @return the failing class
   */
  public Class<?> getFailingClass() {
    return failingClass;
  }
}
