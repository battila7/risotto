package io.risotto.exception;

import io.risotto.Risotto;

/**
 * Thrown when there is attempt to call {@link Risotto#getRootContainer()} prior to adding a
 * <b>valid</b> root container.
 */
public class RootContainerUnsetException extends RuntimeException {
  private static final String MESSAGE = "The root container is not yet instantiated!";

  public RootContainerUnsetException() {
    super(MESSAGE);
  }
}
