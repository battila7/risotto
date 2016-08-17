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

public abstract class CustomContainer extends Container {
  private final List<InstantiatableBinding<?>> bindingList;

  private final List<Dependency<?>> dependencyList;

  private Container parentContainer;

  private String name;

  public CustomContainer() {
    this.bindingList = new ArrayList<>();

    this.dependencyList = new ArrayList<>();
  }

  public <T> Optional<T> getInstance(Class<T> clazz) {
    Dependency<T> dependency = new Dependency<>(clazz);

    return returnInstance(clazz, dependency);
  }

  public <T> Optional<T> getInstance(Class<T> clazz, String name) {
    NamedDependency<T> dependency = new NamedDependency<T>(clazz, name);

    return returnInstance(clazz, dependency);
  }

  public <T> Optional<T> getInstance(Class<T> clazz, Class<? extends Annotation> annotation) {
    AnnotatedDependency<T> dependency = new AnnotatedDependency<T>(clazz, annotation);

    return returnInstance(clazz, dependency);
  }

  @Override
  public void addChildContainer(ContainerSettings containerSettings)
      throws InvalidContainerNameException, ContainerInstantiationException {


    synchronized (lockObject) {
      for (String containerName : childContainerMap.keySet()) {
        if (containerName.equals(containerSettings.getName())) {
          throw new InvalidContainerNameException(containerSettings.getName());
        }
      }

      CustomContainer newContainer;

      try {
        newContainer = containerSettings.getContainerClass().newInstance();
      } catch (IllegalAccessException | InstantiationException e) {
        throw new ContainerInstantiationException(containerSettings.getContainerClass(), e);
      }

      newContainer.setName(containerSettings.getName());

      newContainer.setParentContainer(this);

      childContainerMap.put(containerSettings.getName(), newContainer);
    }
  }

  void configureChildren() throws DependencyResolutionFailedException {
    synchronized (lockObject) {
      for (CustomContainer container : childContainerMap.values()) {
        container.configure();

        container.performResolution();

        container.configureChildren();
      }
    }
  }

  /**
   * Configures the contents of the container. By overriding this method, instances and container
   * containers can be added to the container object.
   */
  protected abstract void configure();

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

  private <T> Optional<T> returnInstance(Class<T> clazz, Dependency<T> dependency) {
    Optional<InstantiatableBinding<?>> bindingOptional = resolve(dependency);

    if (!bindingOptional.isPresent()) {
      return Optional.empty();
    }

    InstantiatableBinding<?> binding = bindingOptional.get();

    return Optional.of(clazz.cast(binding.getInstance()));
  }
}
