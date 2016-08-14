package com.dijon.instantiation;

import static com.dijon.dependency.DependencyDetector.createDetectors;

import com.dijon.dependency.Dependency;
import com.dijon.dependency.DependencyDetector;
import com.dijon.dependency.DependencyInjector;

import java.util.List;
import java.util.Optional;

public class DependencyInjectionInstantiator<T> implements Instantiator<T> {
  private final Class<T> clazz;

  private DependencyInjector<T> injector;

  public DependencyInjectionInstantiator(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public T getInstance() {
    return injector.createInstance();
  }

  @Override
  public Instantiator<T> getBaseInstantiator() {
    return this;
  }

  @Override
  public List<Dependency<?>> getImmediateDependencies() {
    List<DependencyDetector<T>> detectors = createDetectors(clazz);

    List<Dependency<?>> immediateDependencies = null;

    for (DependencyDetector<T> detector : detectors) {
      Optional<List<Dependency<?>>> dependenciesOptional = detector.detectImmediateDependencies();

      if (dependenciesOptional.isPresent()) {
        immediateDependencies = dependenciesOptional.get();

        injector = detector.getInjector();

        break;
      }
    }

    if (immediateDependencies == null) {
      // TODO: THROW IF NOTHING
    }

    return immediateDependencies;
  }
}
