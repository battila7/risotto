package io.risotto;

import static io.risotto.Container.HAS_NO_PARENT;

import io.risotto.exception.ContainerConfigurationException;
import io.risotto.exception.ContainerInstantiationException;
import io.risotto.exception.DependencyResolutionFailedException;
import io.risotto.exception.RootContainerAlreadySetException;
import io.risotto.exception.RootContainerUnsetException;

import java.util.Optional;

/**
 * The main class of the Risotto library. Holds the root container and the complete container tree.
 * Instantiation is prohibited, only contains static methods.
 */
public final class Risotto {
  /**
   * The path that can be used with {@link #getContainer(String)} to retrieve the root container.
   */
  public static final String ROOT_PATH = "";

  /**
   * The delimiter character that can be used to between container names in {@link
   * #getContainer(String)} paths. Note that the delimiter should not be placed after the {@code
   * ROOT_PATH}.
   */
  public static final String CONTAINER_PATH_DELIMITER = "/";

  private static final Object rootContainerLockObject = new Object();

  private static Container rootContainer = null;

  /**
   * Sets the root container and initiates the configuration and dependency resolution process.
   * <p>
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
    }

    return rootContainer;
  }

  /**
   * Gets the container with the specified path. Using this method is equal to the following piece
   * of code:
   * <pre>
   * <code>
   *   // Assuming the root container is already set
   *   Container rootContainer = Risotto.getRootContainer();
   *   rootContainer.getContainer(path);
   * </code>
   * </pre>
   * <p>
   * For more information on how path-based container retrieval works, please see {@link
   * Container#getDescendant(String)}.
   * <p>
   * The root container can be retrieved using {@code Risotto.getContainer(Risotto.ROOT_PATH);}.
   * @param path the path to the desired container relative to the root
   * @return an {@code Optional} that either contains a container instance or is empty if there's no
   * container with the specified path
   * @throws RootContainerUnsetException if the root container is not yet set
   */
  public static Optional<Container> getContainer(String path) throws RootContainerUnsetException {
    synchronized (rootContainerLockObject) {
      if (rootContainer == null) {
        throw new RootContainerUnsetException();
      }
    }

    return rootContainer.getDescendant(path);
  }

  private Risotto() {
    /*
     *  Cannot be instantiated.
     */
  }
}
