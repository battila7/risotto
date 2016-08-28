package io.risotto;

import io.risotto.binding.BasicBinding;
import io.risotto.binding.InstantiatableBinding;
import io.risotto.binding.scope.Scope;
import io.risotto.dependency.AnnotatedDependency;
import io.risotto.dependency.Dependency;
import io.risotto.dependency.NamedDependency;
import io.risotto.exception.DependencyResolutionFailedException;
import io.risotto.exception.InvalidContainerNameException;
import io.risotto.exception.ScopeInstantiationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Abstract dependency container class that must be subclassed by custom dependency containers.
 */
public abstract class Container {
  /**
   * The parent of the root container.
   */
  public static final Container HAS_NO_PARENT = null;

  private final Map<String, Container> childContainerMap;

  private final List<ContainerSettings> configurableChildList;

  private final List<InstantiatableBinding<?>> bindingList;

  private final List<Dependency<?>> dependencyList;

  private Container parentContainer;

  /**
   * Constructs a new {@code Container} instance.
   */
  public Container() {
    this.childContainerMap = new HashMap<>();

    this.configurableChildList = new ArrayList<>();

    this.bindingList = new ArrayList<>();

    this.dependencyList = new ArrayList<>();
  }

  /**
   * Returns an instance of the specified class if there's an appropriate binding in the current
   * container's context.
   *
   * If no instance can be returned, returns an empty {@code Optional}.
   * @param clazz the class of which an instance is requested
   * @param <T> the type of the requested instance
   * @return an {@code Optional} that either contains an instance or is empty
   */
  public final <T> Optional<T> getInstance(Class<T> clazz) {
    Dependency<T> dependency = new Dependency<>(clazz);

    dependency.setOrigin(Scope.GET_INSTANCE_REQUEST);

    return returnInstance(clazz, dependency);
  }

  /**
   * Returns an instance of the specified class if there's an appropriate named binding with the
   * specified name in the current container's context.
   *
   * If no instance can be returned, returns an empty {@code Optional}.
   * @param clazz the class of which an instance is requested
   * @param name the associated name that should be used to retrieve the instance
   * @param <T> the type of the requested instance
   * @return an {@code Optional} that either contains an instance or is empty
   */
  public final <T> Optional<T> getInstance(Class<T> clazz, String name) {
    NamedDependency<T> dependency = new NamedDependency<>(clazz, name);

    dependency.setOrigin(Scope.GET_INSTANCE_REQUEST);

    return returnInstance(clazz, dependency);
  }

  /**
   * Returns an instance of the specified class if there's an appropriate annotated binding with the
   * specified annotation in the current container's context.
   *
   * If no instance can be returned, returns an empty {@code Optional}.
   * @param clazz the class of which an instance is requested
   * @param annotation the associated annotation class that should be used to retrieve the instance
   * @param <T> the type of the requested instance
   * @return an {@code Optional} that either contains an instance or is empty
   */
  public final <T> Optional<T> getInstance(Class<T> clazz, Class<? extends Annotation> annotation) {
    AnnotatedDependency<T> dependency = new AnnotatedDependency<>(clazz, annotation);

    dependency.setOrigin(Scope.GET_INSTANCE_REQUEST);

    return returnInstance(clazz, dependency);
  }

  /**
   * Gets the parent container or {@link Container#HAS_NO_PARENT} if there's no parent container.
   * @return the parent container
   */
  public Container getParentContainer() {
    return parentContainer;
  }

  /**
   * Sets the parent container of this container instance.
   * @param parentContainer the parent container
   */
  /* package */ void setParentContainer(Container parentContainer) {
    this.parentContainer = parentContainer;
  }

  /**
   * Gets the child container with the specified name.
   * @param name the name of the child container
   * @return an {@code Optional} that either contains a container instance or is empty if there's no
   * child with the specified name
   */
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
   * Configures the contents of the container. By overriding this method, bindings and child
   * containers can be added to the container object.
   */
  protected void configure() {
    /**
     * Do nothing by default.
     */
  }

  /**
   * Adds a child container to the current {@code Container} instance (to be more precise a {@code
   * ContainerSettings} object that will be used to add a new child instance).
   * @param containerSettings the settings of the new child container
   * @throws NullPointerException if the {@code containerSettings} parameter is {@code null}
   * @throws InvalidContainerNameException if the name set in the parameter is already used or
   * invalid
   * @see ContainerSettings#container(Class)
   */
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

  /**
   * Adds a new binding to the current container instance that will be used to resolve dependencies
   * and serve {@code getInstance} requests.
   * @param instantiatableBinding the binding to add
   * @throws NullPointerException if the {@code instantiatableBinding} parameter is {@code null}
   * @see BasicBinding#bind(Class)
   */
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
          dependencyList.add(d);
        });
  }

  /**
   * Initiates the dependency resolution process. Dependency resolution is performed using a
   * post-order traversal of the container tree.
   * @throws DependencyResolutionFailedException if a dependency cannot be resolved
   */
  /* package */ void performResolution() throws DependencyResolutionFailedException {
    for (Container childContainer : childContainerMap.values()) {
      childContainer.performResolution();
    }

    for (Dependency<?> dependency : dependencyList) {
      Optional<InstantiatableBinding<?>> bindingOptional = resolve(dependency);

      if (!bindingOptional.isPresent()) {
        throw new DependencyResolutionFailedException(dependency);
      }

      dependency.setResolvingBinding(bindingOptional.get());
    }
  }

  /**
   * Gets the map of child containers.
   * @return the map of child containers
   */
  /* package */ Map<String, Container> getChildContainerMap() {
    return childContainerMap;
  }

  /**
   * Gets the list of children added using {@link #addChild(ContainerSettings)}.
   * @return the list of configurable child containers
   */
  /* package */ List<ContainerSettings> getConfigurableChildList() {
    return configurableChildList;
  }

  /**
   * Imports the appropiate bindings from the current container instance to the specified target
   * container.
   * @param targetContainer the container the bindings should be imported to
   * @see Scope#isImportAllowedTo(Container)
   */
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

  private void setBindingScope(InstantiatableBinding<?> instantiatableBinding)
      throws ScopeInstantiationException {
    try {
      Class<? extends Scope> scopeClass = instantiatableBinding.getScopeClass();

      Constructor<? extends Scope> defaultConstructor = scopeClass.getConstructor();

      defaultConstructor.setAccessible(true);

      Scope bindingScope = defaultConstructor.newInstance();

      bindingScope.setOrigin(this);

      instantiatableBinding.setScope(bindingScope);
    } catch (SecurityException | ReflectiveOperationException | IllegalArgumentException e) {
      throw new ScopeInstantiationException(instantiatableBinding,
          instantiatableBinding.getScopeClass(), e);
    }
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
