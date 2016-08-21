package com.dijon.exception;

import com.dijon.Container;

public class ContainerConfigurationException extends Exception {
  private final Container container;

  public ContainerConfigurationException(Container container, Exception e) {
    super("Configuration failed when configurating container.", e);

    this.container = container;
  }

  public Container getContainer() {
    return container;
  }
}
