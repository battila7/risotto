package com.dijon.binding;

import com.dijon.dependency.Dependency;
import com.dijon.dependency.NamedDependency;

public class NamedBinding<T> extends ComposableBinding<T> {
  private final String name;

  public NamedBinding(Binding<T> binding, String name) {
    super(binding);

    this.name = name;
  }

  public boolean canResolve(Dependency<?> dependency) {
    if (!(dependency instanceof NamedDependency)) {
      return false;
    }

    NamedDependency<?> namedDependency = (NamedDependency<?>)dependency;

    if (!(namedDependency.getName().equals(name))) {
      return false;
    }

    return binding.getBoundedClass().isAssignableFrom(dependency.getBoundedClass());
  }
}
