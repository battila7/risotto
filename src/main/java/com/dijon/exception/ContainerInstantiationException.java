package com.dijon.exception;

import com.dijon.CustomContainer;

public class ContainerInstantiationException extends Exception {
  private static final String MESSAGE = "Could not instantiate container class: ";

  private final Class<? extends CustomContainer> containerClass;

  public ContainerInstantiationException(Class<? extends CustomContainer> containerClass, Throwable cause) {
    super(MESSAGE + containerClass.toString(), cause);

    this.containerClass = containerClass;
  }

  public Class<? extends CustomContainer> getContainerClass() {
    return containerClass;
  }
}
