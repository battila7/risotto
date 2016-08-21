package io.risotto.exception;

public class RootContainerAlreadySetException extends Exception {
  public RootContainerAlreadySetException() {
    super("The root container object is already set by another addRootContainer() call!");
  }
}
