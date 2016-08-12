package com.dijon;

import com.dijon.binding.InstantiatableBinding;
import com.dijon.dependency.AnnotatedDependency;
import com.dijon.dependency.Dependency;
import com.dijon.dependency.NamedDependency;
import com.dijon.exception.ContainerInstantiationException;
import com.dijon.exception.DependencyResolutionFailedException;
import com.dijon.exception.InvalidContainerNameException;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class CustomContainer extends AbstractContainer {
  private final List<InstantiatableBinding<?>> bindingList;

  private final List<Dependency<?>> dependencyList;

  private AbstractContainer parentContainer;

  private String name;

  public CustomContainer() {
    super();

    this.bindingList = new ArrayList<>();

    this.dependencyList = new ArrayList<>();
  }

  public void setParentContainer(AbstractContainer parentContainer) {
    this.parentContainer = parentContainer;
  }

  public void setName(String name) {
    this.name = name;
  }

  public <T> Optional<T> getInstance(Class<T> clazz) throws Exception {
    Dependency<T> dependency = new Dependency<>(clazz);

    return returnInstance(dependency);
  }

  public <T> Optional<T> getInstance(Class<T> clazz, String name) throws Exception {
    NamedDependency<T> dependency = new NamedDependency<T>(clazz, name);

    return returnInstance(dependency);
  }

  public <T> Optional<T> getInstance(Class<T> clazz, Class<? extends Annotation> annotation)
      throws Exception {
    AnnotatedDependency<T> dependency = new AnnotatedDependency<T>(clazz, annotation);

    return returnInstance(dependency);
  }

  @SuppressWarnings("unchecked")
  private <T> Optional<T> returnInstance(Dependency<T> dependency) throws Exception {
    Optional<InstantiatableBinding<?>> bindingOptional = resolve(dependency);

    if (!bindingOptional.isPresent()) {
      return Optional.empty();
    }

    InstantiatableBinding<T> binding = (InstantiatableBinding<T>) bindingOptional.get();

    return Optional.of(binding.getInstance());
  }

  @Override
  public void addChildContainer(Class<? extends CustomContainer> childContainer, String name)
      throws InvalidContainerNameException, ContainerInstantiationException {
    synchronized (lockObject) {
      for (String containerName : childContainerMap.keySet()) {
        if (containerName.equals(name)) {
          throw new InvalidContainerNameException();
        }
      }

      CustomContainer newContainer;

      try {
        newContainer = childContainer.newInstance();
      } catch (IllegalAccessException | InstantiationException e) {
        throw new ContainerInstantiationException();
      }

      newContainer.setName(name);

      newContainer.setParentContainer(this);

      childContainerMap.put(name, newContainer);
    }
  }

  /**
   * Configures the contents of the container. By overriding this method, instances and child
   * containers can be added to the container object.
   */
  protected abstract void configure();

  void configureChildren() throws DependencyResolutionFailedException {
    synchronized (lockObject) {
      for (CustomContainer container : childContainerMap.values()) {
        container.configure();

        container.performResolution();

        container.configureChildren();
      }
    }
  }

  protected void addBinding(InstantiatableBinding<?> instantiatableBinding) {
    bindingList.add(instantiatableBinding);

    List<Dependency<?>> immediateDependencies = instantiatableBinding.getImmediateDependencies();

    for (Dependency<?> dependency : immediateDependencies) {
      if (!dependencyList.contains(dependency)) {
        dependencyList.add(dependency);
      }
    }
  }

  protected void performResolution() throws DependencyResolutionFailedException {
    for (Dependency<?> dependency : dependencyList) {
      Optional<InstantiatableBinding<?>> bindingOptional = resolve(dependency);

      if (!bindingOptional.isPresent()) {
        throw new DependencyResolutionFailedException(dependency);
      }

      dependency.setResolvingBinding(bindingOptional.get());
    }
  }

  @Override
  protected Optional<InstantiatableBinding<?>> resolve(Dependency<?> dependency) {
    for (InstantiatableBinding<?> binding : bindingList) {
      if (binding.canResolve(dependency)) {
        return Optional.of(binding);
      }
    }

    return parentContainer.resolve(dependency);
  }

  @Override
  protected CompletableFuture<Optional<InstantiatableBinding<?>>> resolveAsync(
      Dependency<?> dependency) {
    return null;
  }

  public String getName() {
    return name;
  }
}
