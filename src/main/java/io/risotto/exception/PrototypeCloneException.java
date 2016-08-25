package io.risotto.exception;

public class PrototypeCloneException extends RuntimeException {
  private static final String MESSAGE =
      "Clone failed because there's no proper method or constructor to call";

  public PrototypeCloneException() {
    super(MESSAGE);
  }

  public PrototypeCloneException(Throwable cause) {
    super(cause);
  }
}
