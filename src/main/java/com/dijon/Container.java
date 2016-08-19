package com.dijon;

import com.dijon.binding.InstantiatableBinding;
import com.dijon.dependency.AnnotatedDependency;
import com.dijon.dependency.Dependency;
import com.dijon.dependency.NamedDependency;
import com.dijon.dependency.resolution.Resolver;
import com.dijon.exception.DependencyResolutionFailedException;
import com.dijon.exception.InvalidContainerNameException;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Abstract dependency container class that must be subclassed by custom dependency containers.
 */
public abstract class Container {
  private final Map<String, Container> childContainerMap;

  private final List<ContainerSettings> configurableChildList;

  private final List<InstantiatableBinding<?>> bindingList;

  private final List<Dependency<?>> dependencyList;

  private Container parentContainer;

  private ContainerSettings initialSettings;

  private Resolver resolver;

  public Container() {
    this.childContainerMap = new HashMap<>();

    this.configurableChildList = new ArrayList<>();

    this.bindingList = new ArrayList<>();

    this.dependencyList = new ArrayList<>();
  }

  public final <T> Optional<T> getInstance(Class<T> clazz) {
    Dependency<T> dependency = new Dependency<>(clazz);

    return returnInstance(clazz, dependency);
  }

  public final <T> Optional<T> getInstance(Class<T> clazz, String name) {
    NamedDependency<T> dependency = new NamedDependency<T>(clazz, name);

    return returnInstance(clazz, dependency);
  }

  public final <T> Optional<T> getInstance(Class<T> clazz, Class<? extends Annotation> annotation) {
    AnnotatedDependency<T> dependency = new AnnotatedDependency<T>(clazz, annotation);

    return returnInstance(clazz, dependency);
  }

  public final Optional<Container> getChild(String name) {
    if (name == null) {
      throw new NullPointerException("The name must not be null!");
    }

    Container container = childContainerMap.get(name);

    if (container == null) {
      return Optional.empty();
    }

    return Optional.of(container);
  }

  /**
   * Configures the contents of the container. By overriding this method, instances and container
   * containers can be added to the container object.
   */
  protected abstract void configure();

  protected final void addChild(ContainerSettings containerSettings)
      throws InvalidContainerNameException {
    if (containerSettings == null) {
      throw new NullPointerException("The container settings parameter must not be null!");
    }

    for (ContainerSettings child : configurableChildList) {
      if (child.getName().equals(containerSettings.getName())) {
        throw new InvalidContainerNameException(containerSettings.getName());
      }
    }

    configurableChildList.add(containerSettings);
  }

  protected void addBinding(InstantiatableBinding<?> instantiatableBinding) {
    if (instantiatableBinding == null) {
      throw new NullPointerException("The binding must not be null!");
    }

    bindingList.add(instantiatableBinding);

    List<Dependency<?>> immediateDependencies = instantiatableBinding.getImmediateDependencies();

    for (Dependency<?> dependency : immediateDependencies) {
      if (!dependencyList.contains(dependency)) {
        dependencyList.add(dependency);
      }
    }
  }

  /* package */ void performResolution() throws DependencyResolutionFailedException {
    for (Dependency<?> dependency : dependencyList) {
      Optional<InstantiatableBinding<?>> bindingOptional = resolve(dependency);

      if (!bindingOptional.isPresent()) {
        throw new DependencyResolutionFailedException(dependency);
      }

      dependency.setResolvingBinding(bindingOptional.get());
    }
  }

  /* package */ Map<String, Container> getChildContainerMap() {
    return childContainerMap;
  }

  /* package */ List<ContainerSettings> getConfigurableChildList() {
    return configurableChildList;
  }

  private Optional<InstantiatableBinding<?>> resolve(Dependency<?> dependency) {
    return resolver.resolve(bindingList, dependency, () -> {
      for (Container childContainer : childContainerMap.values()) {
        Optional<InstantiatableBinding<?>> result =
            childContainer.resolve(dependency);

        if (result.isPresent()) {
          return result;
        }
      }

      return Optional.empty();
    });
  }

  private <T> Optional<T> returnInstance(Class<T> clazz, Dependency<T> dependency) {
    Optional<InstantiatableBinding<?>> bindingOptional = resolve(dependency);

    if (!bindingOptional.isPresent()) {
      return Optional.empty();
    }

    InstantiatableBinding<?> binding = bindingOptional.get();

    return Optional.of(clazz.cast(binding.getInstance()));
  }
}
