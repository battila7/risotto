package com.dijon;

import com.dijon.binding.InstantiatableBinding;
import com.dijon.dependency.Dependency;
import com.dijon.exception.ContainerInstantiationException;
import com.dijon.exception.DependencyResolutionFailedException;
import com.dijon.exception.InvalidContainerNameException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Abstract dependency container class that must be subclassed by custom dependency containers.
 */
public abstract class AbstractContainer {
  protected final Map<String, CustomContainer> childContainerMap;

  protected final Object lockObject;

  public AbstractContainer() {
    this.childContainerMap = new HashMap<>();

    this.lockObject = new Object();
  }

  public abstract void addChildContainer(ChildSettings childSettings)
      throws InvalidContainerNameException, ContainerInstantiationException,
      DependencyResolutionFailedException;

  public Optional<CustomContainer> getChildContainer(String name) {
    synchronized (lockObject) {
      for (CustomContainer container : childContainerMap.values()) {
        if (container.getName().equals(name)) {
          return Optional.of(container);
        }
      }
    }

    return Optional.empty();
  }

  protected abstract Optional<InstantiatableBinding<?>> resolve(Dependency<?> dependency);

  protected abstract CompletableFuture<Optional<InstantiatableBinding<?>>> resolveAsync(
      Dependency<?> dependency);
}
