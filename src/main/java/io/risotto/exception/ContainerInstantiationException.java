package io.risotto.exception;

import io.risotto.Container;

public class ContainerInstantiationException extends Exception {
  private static final String MESSAGE = "Could not instantiate container class: ";

  private final Class<? extends Container> containerClass;

  public ContainerInstantiationException(Class<? extends Container> containerClass,
                                         Throwable cause) {
    super(MESSAGE + containerClass.toString(), cause);

    this.containerClass = containerClass;
  }

  public Class<? extends Container> getContainerClass() {
    return containerClass;
  }
}
