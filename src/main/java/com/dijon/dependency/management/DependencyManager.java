package com.dijon.dependency.management;

public interface DependencyManager<T> {
  DependencyDetector<T> getDetector();

  DependencyInjector<T> getInjector();
}
