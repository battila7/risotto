package com.dijon.core;

import com.dijon.core.binding.Binding;

/**
 * Abstract dependency container class that must be subclassed by custom dependency containers.
 */
public abstract class AbstractContainer {
  /**
   * Configures the contents of the container. By overriding this method, instances and child
   * containers can be added to the container object.
   */
  protected abstract void configure();

  /**
   * Adds a child container to this container. The dependency resolution of the passed child
   * container is deferred until the dependency resolution of the current container takes place.
   *
   * Note that if you wish to add two or more containers with the same type then use
   * the {@link AbstractContainer#addChildContainer(Class, String)} method to avoid
   * type collision.
   * @param childContainer the new child container class
   */
  public void addChildContainer(Class<? extends AbstractContainer> childContainer) {
    // TODO: Implement
  }

  /**
   * Adds a child container to this container with the specified name. Behaves the same as {@link
   * AbstractContainer#addChildContainer(Class)}.
   * @param childContainer the new child container class
   * @param name a unique name for the child container
   */
  public void addChildContainer(Class<? extends AbstractContainer> childContainer, String name) {
    // TODO: Implement
  }

  public <T> Binding<T> bind(Class<T> clazz) {
    return null;
  }
}
