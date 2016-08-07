package com.dijon.dependency.management;

import com.dijon.dependency.Dependency;

import java.util.List;
import java.util.Optional;

public class ConstructorDependencyDetector<T> extends DependencyDetector<T> {
  public ConstructorDependencyDetector(Class<T> clazz) {
    super(clazz);
  }

  @Override
  public Optional<List<Dependency>> detectDependencies() {
    return null;
  }
}
