package com.dijon.binding;

import com.dijon.dependency.Dependency;
import com.dijon.dependency.NamedDependency;

public class NamedBinding<T> extends TerminableBinding<T> {
  private final String name;

  public NamedBinding(Class<T> clazz, String name) {
    super(clazz);

    this.name = name;
  }

  public boolean canResolve(Dependency<?> dependency) {
    if (!(dependency instanceof NamedDependency)) {
      return false;
    }

    NamedDependency<?> namedDependency = (NamedDependency<?>) dependency;

    if (!(namedDependency.getName().equals(name))) {
      return false;
    }

    return dependency.getBoundedClass().isAssignableFrom(boundedClass);
  }
}
