package com.dijon;

import static com.dijon.Container.HAS_NO_PARENT;

import com.dijon.exception.ContainerInstantiationException;
import com.dijon.exception.RootContainerAlreadySetException;
import com.dijon.exception.RootContainerUnsetException;

public final class Dijon {
  private static final Object rootContainerLockObject = new Object();

  private static Container rootContainer = null;

  public static Container addRootContainer(ContainerSettings containerSettings) throws
      RootContainerAlreadySetException, ContainerInstantiationException {
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

  private Dijon() {
    /*
     *  Cannot be instantiated.
     */
  }
}
