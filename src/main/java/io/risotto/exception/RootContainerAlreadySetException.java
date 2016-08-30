package io.risotto.exception;

import io.risotto.ContainerSettings;
import io.risotto.Risotto;

/**
 * Arises when the root container has already been set by another {@link
 * Risotto#addRootContainer(ContainerSettings)} call.
 */
public class RootContainerAlreadySetException extends Exception {
  private static final String MESSAGE =
      "The root container object is already set by another addRootContainer() call!";

  /**
   * Constructs a new instance.
   */
  public RootContainerAlreadySetException() {
    super(MESSAGE);
  }
}
