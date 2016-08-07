package com.dijon.dependency.management;

import com.dijon.dependency.Dependency;

import java.util.List;
import java.util.Optional;

public abstract class DependencyDetector<T> {
  protected final Class<T> clazz;

  public DependencyDetector(Class<T> clazz) {
    this.clazz = clazz;
  }

  public abstract Optional<List<Dependency>> detectDependencies();
}
