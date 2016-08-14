package com.dijon;

import com.dijon.binding.InstantiatableBinding;
import com.dijon.dependency.Dependency;
import com.dijon.exception.ContainerInstantiationException;
import com.dijon.exception.DependencyResolutionFailedException;
import com.dijon.exception.InvalidContainerNameException;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public final class RootContainer extends AbstractContainer {
  @Override
  public void addChildContainer(Class<? extends CustomContainer> childContainerClass, String name) throws
      InvalidContainerNameException, ContainerInstantiationException,
      DependencyResolutionFailedException {
    if (childContainerClass == null) {
      throw new NullPointerException("The container class must not be null!");
    }

    if (name == null) {
      throw new NullPointerException("The container name must not be null!");
    }

    CustomContainer newContainer;

    synchronized (lockObject) {
      for (String containerName : childContainerMap.keySet()) {
        if (containerName.equals(name)) {
          throw new InvalidContainerNameException(name);
        }
      }

      try {
        newContainer = childContainerClass.newInstance();
      } catch (IllegalAccessException | InstantiationException e) {
        throw new ContainerInstantiationException(childContainerClass, e);
      }

      newContainer.setName(name);

      newContainer.setParentContainer(this);

      childContainerMap.put(name, newContainer);
    }

    newContainer.configure();

    newContainer.performResolution();

    newContainer.configureChildren();
  }

  @Override
  protected Optional<InstantiatableBinding<?>> resolve(Dependency<?> dependency) {
    return Optional.empty();
  }

  @Override
  protected CompletableFuture<Optional<InstantiatableBinding<?>>> resolveAsync(
      Dependency<?> dependency) {
    return null;
  }
}
