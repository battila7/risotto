package io.risotto;

import io.risotto.configurator.Configurator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class hold the settings needed for container instantiation and configuration.
 */
public final class ContainerSettings {
  private final Class<? extends Container> containerClass;

  private final List<Configurator> configuratorList;

  private String name;

  /**
   * Creates a new {@code ContainerSettings} object using the specified container class.
   * @param containerClass the container class to be instantiated
   * @return a new {@code ContainerSettings} object
   */
  public static ContainerSettings container(Class<? extends Container> containerClass) {
    return new ContainerSettings(containerClass);
  }

  private ContainerSettings(Class<? extends Container> containerClass) {
    this.containerClass = containerClass;

    this.name = containerClass.getName();

    this.configuratorList = new ArrayList<>();
  }

  /**
   * Assigns the specified to the container to be instantiated using the settings object.
   * @param name The name of the container. Must be a unique container name in the parent container
   * of the container to be instantiated.
   * @return the current {@code ContainerSettings} instance
   */
  public ContainerSettings as(String name) {
    this.name = name;

    return this;
  }

  /**
   * Assigns the specified {@link Configurator} objects to the container that will be used to
   * configure the container.
   * @param configurators the {@code Configurator} objects to be used
   * @return the current {@code ContainerSettings} instance
   */
  public ContainerSettings withConfigurators(Configurator... configurators) {
    Collections.addAll(configuratorList, configurators);

    return this;
  }

  /**
   * Gets the container class.
   * @return the container class
   */
  public Class<? extends Container> getContainerClass() {
    return containerClass;
  }

  /**
   * Gets the list of assigned configurators.
   * @return the list of assigned configurators
   */
  public List<Configurator> getConfiguratorList() {
    return configuratorList;
  }

  /**
   * Gets the name assigned to the container to be instantiated.
   * @return the name
   */
  public String getName() {
    return name;
  }
}
