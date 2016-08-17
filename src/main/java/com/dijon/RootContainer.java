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
  public void addChildContainer(ChildSettings childSettings)
      throws
      InvalidContainerNameException, ContainerInstantiationException,
      DependencyResolutionFailedException {
    if (childSettings == null) {
      throw new NullPointerException("The child settings parameter must not be null!");
    }

    CustomContainer newContainer;

    synchronized (lockObject) {
      for (String containerName : childContainerMap.keySet()) {
        if (containerName.equals(childSettings.getName())) {
          throw new InvalidContainerNameException(childSettings.getName());
        }
      }

      try {
        newContainer = childSettings.getContainerClass().newInstance();
      } catch (IllegalAccessException | InstantiationException e) {
        throw new ContainerInstantiationException(childSettings.getContainerClass(), e);
      }

      newContainer.setName(childSettings.getName());

      newContainer.setParentContainer(this);

      childContainerMap.put(childSettings.getName(), newContainer);
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
