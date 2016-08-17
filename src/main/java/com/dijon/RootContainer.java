package com.dijon;

import com.dijon.binding.InstantiatableBinding;
import com.dijon.dependency.Dependency;
import com.dijon.exception.ContainerInstantiationException;
import com.dijon.exception.DependencyResolutionFailedException;
import com.dijon.exception.InvalidContainerNameException;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public final class RootContainer extends Container {
  @Override
  public void addChildContainer(ContainerSettings containerSettings)
      throws
      InvalidContainerNameException, ContainerInstantiationException,
      DependencyResolutionFailedException {
    if (containerSettings == null) {
      throw new NullPointerException("The container settings parameter must not be null!");
    }

    CustomContainer newContainer;

    synchronized (lockObject) {
      for (String containerName : childContainerMap.keySet()) {
        if (containerName.equals(containerSettings.getName())) {
          throw new InvalidContainerNameException(containerSettings.getName());
        }
      }

      try {
        newContainer = containerSettings.getContainerClass().newInstance();
      } catch (IllegalAccessException | InstantiationException e) {
        throw new ContainerInstantiationException(containerSettings.getContainerClass(), e);
      }

      newContainer.setName(containerSettings.getName());

      newContainer.setParentContainer(this);

      childContainerMap.put(containerSettings.getName(), newContainer);
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
