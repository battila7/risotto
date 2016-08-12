package com.dijon;

import com.dijon.binding.InstantiatableBinding;
import com.dijon.dependency.Dependency;
import com.dijon.exception.ContainerInstantiationException;
import com.dijon.exception.InvalidContainerNameException;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Attila on 2016. 08. 11..
 */
public final class RootContainer extends AbstractContainer {
  public RootContainer() {
    super();
  }

  @Override
  public void addChildContainer(Class<? extends CustomContainer> childContainer, String name) throws
      InvalidContainerNameException, ContainerInstantiationException {
    CustomContainer newContainer;

    synchronized (lockObject) {
      for (String containerName : childContainerMap.keySet()) {
        if (containerName.equals(name)) {
          throw new InvalidContainerNameException();
        }
      }

      try {
        newContainer = childContainer.newInstance();
      } catch (IllegalAccessException | InstantiationException e) {
        throw new ContainerInstantiationException();
      }

      childContainerMap.put(name, newContainer);
    }

    newContainer.configure();

    newContainer.configureChildren();;
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
