package io.risotto.instantiation;

import io.risotto.dependency.Dependency;

import java.util.List;

public interface Instantiator<T> {
  T getInstance();

  Instantiator<T> getBaseInstantiator();

  List<Dependency<?>> getImmediateDependencies();
}
