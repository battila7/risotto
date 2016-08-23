package io.risotto;

import static io.risotto.Container.HAS_NO_PARENT;

import io.risotto.exception.ContainerConfigurationException;
import io.risotto.exception.ContainerInstantiationException;
import io.risotto.exception.DependencyResolutionFailedException;
import io.risotto.exception.RootContainerAlreadySetException;
import io.risotto.exception.RootContainerUnsetException;

/**
 * The main class of the Risotto library. Holds the root container and the complete container tree.
 * Instantiation is prohibited, only contains static methods.
 */
public final class Risotto {
  private static final Object rootContainerLockObject = new Object();

  private static Container rootContainer = null;

  /**
   * Sets the root container and initiates the configuration and dependency resolution process.
   *
   * This method should be called only once during the lifetime of an application. If there are
   * multiple attempts to set the root container, {@code RootContainerAlreadySetException} is
   * thrown.
   * @param containerSettings the settings object containing the settings of the root controller
   * class
   * @return the root container instance
   * @throws RootContainerAlreadySetException if the root container has already been set by a
   * previous call on this method
   * @throws ContainerInstantiationException if a container class in the container tree could not
   * been instantiated
   * @throws ContainerConfigurationException if a container class in the container tree could not
   * been configured
   * @throws DependencyResolutionFailedException if a dependency could not been resolved by any
   * binding
   */
  public static Container addRootContainer(ContainerSettings containerSettings) throws
      RootContainerAlreadySetException, ContainerInstantiationException,
      ContainerConfigurationException, DependencyResolutionFailedException {
    if (containerSettings == null) {
      throw new NullPointerException("The container settings parameter must not be null!");
    }

    synchronized (rootContainerLockObject) {
      if (rootContainer != null) {
        throw new RootContainerAlreadySetException();
      }

      ContainerConfigurator configurator =
          new ContainerConfigurator(containerSettings, HAS_NO_PARENT);

      rootContainer = configurator.instantiateContainer();

      configurator.configureContainer();

      rootContainer.performResolution();

      return rootContainer;
    }
  }

  /**
   * Gets the root container instance.
   * @return the root container instance
   * @throws RootContainerUnsetException if the root container instance is not yet set
   */
  public static Container getRootContainer() throws RootContainerUnsetException {
    synchronized (rootContainerLockObject) {
      if (rootContainer == null) {
        throw new RootContainerUnsetException();
      }

      return rootContainer;
    }
  }

  private Risotto() {
    /*
     *  Cannot be instantiated.
     */
  }
}
