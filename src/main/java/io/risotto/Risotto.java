package io.risotto;

import static io.risotto.Container.HAS_NO_PARENT;

import io.risotto.exception.ContainerConfigurationException;
import io.risotto.exception.ContainerInstantiationException;
import io.risotto.exception.DependencyResolutionFailedException;
import io.risotto.exception.RootContainerAlreadySetException;
import io.risotto.exception.RootContainerUnsetException;

public final class Risotto {
  private static final Object rootContainerLockObject = new Object();

  private static Container rootContainer = null;

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
