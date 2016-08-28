package io.risotto;

import io.risotto.configurator.Configurator;
import io.risotto.exception.InvalidContainerNameException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * This class hold the settings needed for container instantiation and configuration.
 */
public final class ContainerSettings {
  private static final Predicate<String> containerNamePredicate =
      Pattern.compile("[^/\\s]+").asPredicate();

  private final Class<? extends Container> containerClass;

  private final List<Configurator> configuratorList;

  private String name;

  /**
   * Creates a new {@code ContainerSettings} object using the specified container class.
   * @param containerClass the container class to be instantiated
   * @return a new {@code ContainerSettings} object
   * @throws NullPointerException if the continer class is {@code null}
   */
  public static ContainerSettings container(Class<? extends Container> containerClass) {
    if (containerClass == null) {
      throw new NullPointerException("The container class must not be null!");
    }

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
   * @throws InvalidContainerNameException if the specified name is invalid
   * @throws NullPointerException if the name is {@code null}
   */
  public ContainerSettings as(String name) {
    if (name == null) {
      throw new NullPointerException("The name must not be null!");
    }

    if (!containerNamePredicate.test(name)) {
      throw new InvalidContainerNameException(name);
    }

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
