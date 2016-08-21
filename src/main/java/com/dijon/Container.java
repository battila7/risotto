package com.dijon;

import static java.util.stream.Collectors.toList;

import com.dijon.binding.InstantiatableBinding;
import com.dijon.binding.scope.Scope;
import com.dijon.dependency.AnnotatedDependency;
import com.dijon.dependency.Dependency;
import com.dijon.dependency.NamedDependency;
import com.dijon.exception.DependencyResolutionFailedException;
import com.dijon.exception.InvalidContainerNameException;
import com.dijon.exception.ScopeInstantiationException;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Abstract dependency container class that must be subclassed by custom dependency containers.
 */
public abstract class Container {
  public static final Container HAS_NO_PARENT = null;

  private final Map<String, Container> childContainerMap;

  private final List<ContainerSettings> configurableChildList;

  private final List<InstantiatableBinding<?>> bindingList;

  private final Set<Dependency<?>> dependencySet;

  private Container parentContainer;

  public Container() {
    this.childContainerMap = new HashMap<>();

    this.configurableChildList = new ArrayList<>();

    this.bindingList = new ArrayList<>();

    this.dependencySet = new HashSet<>();
  }

  public final <T> Optional<T> getInstance(Class<T> clazz) {
    Dependency<T> dependency = new Dependency<>(clazz);

    dependency.setOrigin(Scope.GET_INSTANCE_REQUEST);

    return returnInstance(clazz, dependency);
  }

  public final <T> Optional<T> getInstance(Class<T> clazz, String name) {
    NamedDependency<T> dependency = new NamedDependency<T>(clazz, name);

    dependency.setOrigin(Scope.GET_INSTANCE_REQUEST);

    return returnInstance(clazz, dependency);
  }

  public final <T> Optional<T> getInstance(Class<T> clazz, Class<? extends Annotation> annotation) {
    AnnotatedDependency<T> dependency = new AnnotatedDependency<T>(clazz, annotation);

    dependency.setOrigin(Scope.GET_INSTANCE_REQUEST);

    return returnInstance(clazz, dependency);
  }

  public Container getParentContainer() {
    return parentContainer;
  }

  /* package */ void setParentContainer(Container parentContainer) {
    this.parentContainer = parentContainer;
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

    // TODO: Check if exists
    bindingList.add(instantiatableBinding);

    setBindingScope(instantiatableBinding);

    instantiatableBinding.getImmediateDependencies().stream()
        .forEach(d -> {
          d.setOrigin(this);
          dependencySet.add(d);
        });
  }

  private void setBindingScope(InstantiatableBinding<?> instantiatableBinding)
      throws ScopeInstantiationException {
    try {
      Class<? extends Scope> scopeClass = instantiatableBinding.getScopeClass();

      Scope bindingScope = scopeClass.newInstance();

      bindingScope.setOrigin(this);

      instantiatableBinding.setScope(bindingScope);
    } catch (InstantiationException | IllegalAccessException e) {
      throw new ScopeInstantiationException(instantiatableBinding.getScopeClass(), e);
    }
  }

  /* package */ void performResolution() throws DependencyResolutionFailedException {
    for (Container childContainer : childContainerMap.values()) {
      childContainer.performResolution();
    }

    for (Dependency<?> dependency : dependencySet) {
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

  /* package */ void importBindingsTo(Container targetContainer) {
    bindingList.stream()
        .filter(b -> b.isImportAllowedTo(targetContainer))
        .forEach(b -> targetContainer.bindingList.add(b));
  }

  private Optional<InstantiatableBinding<?>> resolve(Dependency<?> dependency) {
    for (InstantiatableBinding<?> binding : bindingList) {
      if (binding.canResolve(dependency)) {
        return Optional.of(binding);
      }
    }

    return childContainerMap.values().stream()
        .map(c -> c.resolve(dependency))
        .filter(Optional::isPresent)
        .findAny()
        .orElse(Optional.empty());
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
