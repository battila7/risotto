package io.risotto.exception;

import io.risotto.Container;

/**
 * This kind of exception arises when {@code Risotto} fails to configure a container. The container
 * was successfully instantiated, however the {@link Container#configure()} method or some of the
 * default or custom {@code Configurator}s throw an exception.
 */
public class ContainerConfigurationException extends Exception {
  private final Container container;

  /**
   * Constructs a new instance with the erroneous container and the original exception.
   * @param container the container {@code Risotto} could not configure
   * @param e the cause of the exception
   */
  public ContainerConfigurationException(Container container, Exception e) {
    super("Configuration failed when configurating container.", e);

    this.container = container;
  }

  /**
   * Gets the container {@code Risotto} could not configure.
   * @return the container.
   */
  public Container getContainer() {
    return container;
  }
}
