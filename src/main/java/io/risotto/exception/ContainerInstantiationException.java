package io.risotto.exception;

import io.risotto.Container;

/**
 * Occurs when a {@code Risotto} could not instantiate a container class.
 */
public class ContainerInstantiationException extends Exception {
  private static final String MESSAGE = "Could not instantiate container class: ";

  private final Class<? extends Container> containerClass;

  /**
   * Constructs a new instance with the erroneous container class and the cause.
   * @param containerClass the container {@code Risotto} could not instantiate
   * @param cause the cause of the exception
   */
  public ContainerInstantiationException(Class<? extends Container> containerClass,
                                         Throwable cause) {
    super(MESSAGE + containerClass.toString(), cause);

    this.containerClass = containerClass;
  }

  /**
   * Gets the container class {@code Risotto} could not instantiate.
   * @return the container class
   */
  public Class<? extends Container> getContainerClass() {
    return containerClass;
  }
}
