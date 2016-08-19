package com.dijon.dependency.resolution;

import com.dijon.binding.InstantiatableBinding;
import com.dijon.dependency.Dependency;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class ChildFirstResolver implements Resolver {
  @Override
  public Optional<InstantiatableBinding<?>> resolve(List<InstantiatableBinding<?>> bindings,
                                                    Dependency<?> dependency,
                                                    Supplier<Optional<InstantiatableBinding<?>>> childResolver) {
    Optional<InstantiatableBinding<?>> childBinding = childResolver.get();

    if (childBinding.isPresent()) {
      return childBinding;
    }

    for (InstantiatableBinding<?> binding : bindings) {
      if (binding.canResolve(dependency)) {
        return Optional.of(binding);
      }
    }

    return Optional.empty();
  }
}
