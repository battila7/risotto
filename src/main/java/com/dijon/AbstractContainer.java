package com.dijon;

import com.dijon.binding.InstantiatableBinding;
import com.dijon.dependency.Dependency;
import com.dijon.exception.InvalidContainerNameException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Abstract dependency container class that must be subclassed by custom dependency containers.
 */
public abstract class AbstractContainer {
  protected List<CustomContainer> childContainerList;

  protected Object lockObject = new Object();

  /**
   * Adds a child container to this container. The dependency resolution of the passed child
   * container is deferred until the dependency resolution of the current container takes place.
   *
   * Note that if you wish to add two or more containers with the same type then use the {@link
   * AbstractContainer#addChildContainer(Class, String)} method to avoid type collision.
   * @param childContainer the new child container class
   */
  public void addChildContainer(Class<? extends CustomContainer> childContainer) throws
      InvalidContainerNameException {
    addChildContainer(childContainer, childContainer.getName());
  }

  /**
   * Adds a child container to this container with the specified name. Behaves the same as {@link
   * AbstractContainer#addChildContainer(Class)}.
   * @param childContainer the new child container class
   * @param name a unique name for the child container
   */
  public abstract void addChildContainer(Class<? extends CustomContainer> childContainer,
                                         String name) throws InvalidContainerNameException;

  public abstract Optional<CustomContainer> getChildContainer(String name);

  protected abstract Optional<InstantiatableBinding<?>> resolve(Dependency<?> dependency);

  protected abstract CompletableFuture<Optional<InstantiatableBinding<?>>> resolveAsync(
      Dependency<?> dependency);
}
