package com.dijon.instantiation;

import com.dijon.dependency.Dependency;
import com.dijon.exception.DependencyDetectionException;

import java.util.List;

public interface Instantiator<T> {
  T getInstance();

  Instantiator<T> getBaseInstantiator();

  List<Dependency<?>> getImmediateDependencies();
}
