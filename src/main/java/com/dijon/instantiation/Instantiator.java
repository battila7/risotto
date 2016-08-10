package com.dijon.instantiation;

import com.dijon.dependency.Dependency;

import java.util.List;

/**
 * Created by Attila on 2016. 08. 10..
 */
public interface Instantiator<T> {
  T getInstance();

  void instantiate();

  Instantiator<T> getBaseInstantiator();

  List<Dependency<?>> getImmediateDependencies();
}
