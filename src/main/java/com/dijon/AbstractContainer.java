package com.dijon;

import com.dijon.binding.ClassBinding;
import com.dijon.binding.InstanceBinding;
import com.dijon.binding.InstantiatableBinding;
import com.dijon.dependency.Dependency;
import com.dijon.dependency.management.DependencyInjector;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Abstract dependency container class that must be subclassed by custom dependency containers.
 */
public abstract class AbstractContainer {
  private Map<Class, List<InstantiatableBinding<?>>> bindingMap;

  private List<AbstractContainer> childContainerList;

  /**
   * Configures the contents of the container. By overriding this method, instances and child
   * containers can be added to the container object.
   */
  protected abstract void configure();

  /**
   * Adds a child container to this container. The dependency resolution of the passed child
   * container is deferred until the dependency resolution of the current container takes place.
   *
   * Note that if you wish to add two or more containers with the same type then use the {@link
   * AbstractContainer#addChildContainer(Class, String)} method to avoid type collision.
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

  public <T> void addBinding(InstanceBinding<T> instanceBinding) {

  }

  public boolean hasBindingFor(Class clazz) {
    for (Class cls : bindingMap.keySet()) {
      if (cls.isAssignableFrom(clazz)) {
        return true;
      }
    }

    return false;
  }

  public <T> Optional<DependencyInjector<? extends T>> resolveDependency(Dependency<T> dependency) {
    return null;
  }
}
