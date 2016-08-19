package com.dijon.dependency.resolution;

import com.dijon.binding.InstantiatableBinding;
import com.dijon.dependency.Dependency;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public interface Resolver {
  Optional<InstantiatableBinding<?>> resolve(List<InstantiatableBinding<?>> bindings,
                                             Dependency<?> dependency,
                                             Supplier<Optional<InstantiatableBinding<?>>> childResolver);
}
