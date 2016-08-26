package io.risotto.exception;

/**
 * This kind of exception is thrown when cloning cannot be performed. If the thrown exception has no
 * root cause then Risotto was unable to find a method or constructor that can be used to clone the
 * prototype.
 */
public class PrototypeCloneException extends RuntimeException {
  private static final String MESSAGE =
      "Clone failed because there's no proper method or constructor to call.";

  /**
   * Constructs a new instance with the default message and no root cause.
   */
  public PrototypeCloneException() {
    super(MESSAGE);
  }

  /**
   * Constructs a new instance with the causing {@code Throwable}.
   * @param cause the cause of the exception
   */
  public PrototypeCloneException(Throwable cause) {
    super(cause);
  }
}
