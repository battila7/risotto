package com.dijon;

import com.dijon.binding.InstantiatableBinding;
import com.dijon.dependency.Dependency;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Attila on 2016. 08. 11..
 */
public final class RootContainer extends AbstractContainer {
  @Override
  public Optional<CustomContainer> getChildContainer(String name) {
    synchronized (lockObject) {
      for (CustomContainer container : childContainerList) {
        if (container.getName().equals(name)) {
          return Optional.of(container);
        }
      }

      return Optional.empty();
    }
  }

  @Override
  public void addChildContainer(Class<? extends CustomContainer> childContainer, String name) {
    synchronized (lockObject) {
      for (CustomContainer container : childContainerList) {
        if (container.getName().equals(name)) {

        }
      }
    }
  }

  @Override
  public void addChildContainer(Class<? extends CustomContainer> childContainer) {

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
