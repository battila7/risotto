package com.dijon;

import com.dijon.binding.InstanceBinding;
import com.dijon.binding.InstantiatableBinding;
import com.dijon.dependency.Dependency;
import com.dijon.exception.DependencyResolutionFailedException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class CustomContainer extends AbstractContainer {
  protected final AbstractContainer parentContainer;

  private final String name;

  private List<InstanceBinding<?>> bindingList;

  private List<Dependency<?>> dependencyList;

  public CustomContainer(AbstractContainer parentContainer, String name) {
    this.parentContainer = parentContainer;

    this.name = name;
  }

  /**
   * Configures the contents of the container. By overriding this method, instances and child
   * containers can be added to the container object.
   */
  protected abstract void configure();

  protected void addBinding(InstanceBinding<?> instanceBinding) {
    bindingList.add(instanceBinding);

    List<Dependency<?>> immediateDependencies = instanceBinding.getImmediateDependencies();

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
