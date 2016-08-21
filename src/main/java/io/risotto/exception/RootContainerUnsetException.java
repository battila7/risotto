package io.risotto.exception;

public class RootContainerUnsetException extends RuntimeException {
  private static final String MESSAGE = "The root container is not yet instantiated!";

  public RootContainerUnsetException() {
    super(MESSAGE);
  }
}
