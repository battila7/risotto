package com.dijon;

import com.dijon.binding.InstantiatableBinding;
import com.dijon.dependency.Dependency;
import com.dijon.exception.InvalidContainerNameException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Abstract dependency container class that must be subclassed by custom dependency containers.
 */
public abstract class Container {
  private final Map<String, Container> childContainerMap;

  private final List<ChildSettings> configurableChildList;

  public Container() {
    this.childContainerMap = new HashMap<>();

    this.configurableChildList = new ArrayList<>();
  }

  protected void addChild(ChildSettings childSettings)
      throws InvalidContainerNameException {
    for (ChildSettings child : configurableChildList) {
      if (child.getName().equals(childSettings.getName())) {
        throw new InvalidContainerNameException(childSettings.getName());
      }
    }

    configurableChildList.add(childSettings);
  }

  public Optional<Container> getChildContainer(String name) {
    Container container = childContainerMap.get(name);

    if (container == null) {
      return Optional.empty();
    }

    return Optional.of(container);
  }

  protected abstract Optional<InstantiatableBinding<?>> resolve(Dependency<?> dependency);

  protected abstract CompletableFuture<Optional<InstantiatableBinding<?>>> resolveAsync(
      Dependency<?> dependency);
}
