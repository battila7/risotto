package com.dijon.dependency.instantiation;

/**
 * Created by Attila on 2016. 08. 10..
 */
public interface Instantiator<T> {
  T getInstance();

  void instantiate();

  Instantiator<T> getBaseInstantiator();
}
