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
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Abstract dependency container class that must be subclassed by custom dependency containers.
 */
public abstract class Container {
  /**
   * Constant used as the parent of the root container.
   */
  public static final Container HAS_NO_PARENT = null;

  private static final String EMPTY_STRING = "";

  private final Map<String, Container> childContainerMap;

  private final List<ContainerSettings> configurableChildList;

  private final List<InstantiatableBinding<?>> bindingList;

  private final List<Dependency<?>> dependencyList;

  private Container parentContainer;

  private String name;

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
   * <p>
   * If no instance can be returned, returns an empty {@code Optional}.
   * @param clazz the class of which an instance is requested
   * @param <T> the type of the requested instance
   * @return an {@code Optional} that either contains an instance or is empty
   * @throws NullPointerException if the class is {@code null}
   */
  public final <T> Optional<T> getInstance(Class<T> clazz) {
    if (clazz == null) {
      throw new NullPointerException("The class must not be null!");
    }

    Dependency<T> dependency = new Dependency<>(clazz);

    dependency.setOrigin(Scope.GET_INSTANCE_REQUEST);

    return returnInstance(clazz, dependency);
  }

  /**
   * Returns an instance of the specified class if there's an appropriate named binding with the
   * specified name in the current container's context.
   * <p>
   * If no instance can be returned, returns an empty {@code Optional}.
   * @param clazz the class of which an instance is requested
   * @param name the associated name that should be used to retrieve the instance
   * @param <T> the type of the requested instance
   * @return an {@code Optional} that either contains an instance or is empty
   * @throws NullPointerException if class or name is {@code null}
   */
  public final <T> Optional<T> getInstance(Class<T> clazz, String name) {
    if ((clazz == null) || (name == null)) {
      throw new NullPointerException("The clazz and the name must not be null!");
    }

    NamedDependency<T> dependency = new NamedDependency<>(clazz, name);

    dependency.setOrigin(Scope.GET_INSTANCE_REQUEST);

    return returnInstance(clazz, dependency);
  }

  /**
   * Returns an instance of the specified class if there's an appropriate annotated binding with the
   * specified annotation in the current container's context.
   * <p>
   * If no instance can be returned, returns an empty {@code Optional}.
   * @param clazz the class of which an instance is requested
   * @param annotation the associated annotation class that should be used to retrieve the instance
   * @param <T> the type of the requested instance
   * @return an {@code Optional} that either contains an instance or is empty
   * @throws NullPointerException if class or annotation is {@code null}
   */
  public final <T> Optional<T> getInstance(Class<T> clazz, Class<? extends Annotation> annotation) {
    if ((clazz == null) || (annotation == null)) {
      throw new NullPointerException("The clazz and the annotation must not be null!");
    }

    AnnotatedDependency<T> dependency = new AnnotatedDependency<>(clazz, annotation);

    dependency.setOrigin(Scope.GET_INSTANCE_REQUEST);

    return returnInstance(clazz, dependency);
  }

  /**
   * Gets the parent container or returns {@link Container#HAS_NO_PARENT} if there's no parent
   * container.
   * @return the parent container
   */
  public final Container getParentContainer() {
    return parentContainer;
  }

  /**
   * Gets the name of the container.
   * @return the name of the container
   */
  public final String getName() {
    return name;
  }

  /**
   * Sets the parent container of this container instance.
   * @param parentContainer the parent container
   */
  /* package */ void setParentContainer(Container parentContainer) {
    this.parentContainer = parentContainer;
  }

  /**
   * Gets the descendant container with the specified path relative to the current container. This
   * means that only descendants of the current container are used for retrieval. The current
   * container can be retrieved by passing the empty string to the method. Subsequent container
   * names must be delimited with {@value Risotto#CONTAINER_PATH_DELIMITER}.
   * <p>
   * For example if you have the following container structure:
   * <pre>
   * <code>
   *   A(current) &lt;- B &lt;- C &lt;- D
   * </code>
   * </pre>
   * then you can retrieve {@code C} using
   * <pre>
   * <code>
   *   container.getDescendant("B/C/D");
   * </code>
   * </pre>
   * @param path the relative path to the descendant container
   * @return an {@code Optional} that either contains a container instance or is empty if there's no
   * descendant with the specified path
   * @throws NullPointerException if the path is {@code null}
   */
  public final Optional<Container> getDescendant(String path) {
    if (path == null) {
      throw new NullPointerException("The path must not be null!");
    }

    if (EMPTY_STRING.equals(path)) {
      return Optional.of(this);
    }

    List<String> fragmentList =
        new LinkedList<>(Arrays.asList(path.split(Risotto.CONTAINER_PATH_DELIMITER)));

    return getDescendantFromArray(fragmentList);
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
   * @throws InvalidContainerNameException if the name set in the parameter is already used
   * @see ContainerSettings#container(Class)
   */
  protected final void addChild(ContainerSettings containerSettings) {
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
  protected final void addBinding(InstantiatableBinding<?> instantiatableBinding) {
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
  /* package */ final void performResolution() throws DependencyResolutionFailedException {
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
   * Sets the name of the container.
   * @param name the name of the container
   */
  /* package */ final void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the map of child containers.
   * @return the map of child containers
   */
  /* package */ final Map<String, Container> getChildContainerMap() {
    return childContainerMap;
  }

  /**
   * Gets the list of children added using {@link #addChild(ContainerSettings)}.
   * @return the list of configurable child containers
   */
  /* package */ final List<ContainerSettings> getConfigurableChildList() {
    return configurableChildList;
  }

  /**
   * Imports the appropiate bindings from the current container instance to the specified target
   * container.
   * @param targetContainer the container the bindings should be imported to
   * @see Scope#isImportAllowedTo(Container)
   */
  /* package */ final void importBindingsTo(Container targetContainer) {
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

  private Optional<Container> getDescendantFromArray(List<String> pathFragmentList) {
    if (pathFragmentList.isEmpty()) {
      return Optional.of(this);
    }

    Container child = childContainerMap.get(pathFragmentList.remove(0));

    if (child == null) {
      return Optional.empty();
    }

    return child.getDescendantFromArray(pathFragmentList);
  }

  @Override
  public String toString() {
    return "Container{" +
        ", name='" + name + '\'' +
        "parentContainer=" + parentContainer +
        '}';
  }
}
