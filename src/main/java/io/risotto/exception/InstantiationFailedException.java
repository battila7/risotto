package io.risotto.exception;

/**
 * Thrown when {@code Risotto} is not able to instantiate a class.
 */
public class InstantiationFailedException extends RuntimeException {
  /**
   * Constructs a new instance with the cause.
   * @param cause the cause of the exception
   */
  public InstantiationFailedException(Throwable cause) {
    super(cause);
  }
}
