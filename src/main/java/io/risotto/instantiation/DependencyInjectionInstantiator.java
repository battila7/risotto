package io.risotto.instantiation;

import static io.risotto.dependency.DependencyDetector.createDetectors;

import io.risotto.binding.ClassBinding;
import io.risotto.dependency.Dependency;
import io.risotto.dependency.DependencyDetector;
import io.risotto.dependency.DependencyInjector;
import io.risotto.exception.DependencyDetectionException;

import java.util.List;
import java.util.Optional;

/**
 * Instantiator implementation that uses dependency injection to create new instances. Uses
 * detectors to detect the dependencies of the class to instantiate and injectors for the
 * actual object creation process. The default implementation for {@link ClassBinding}s.
 * @param <T> the type of the object to be instantiated
 */
public class DependencyInjectionInstantiator<T> implements Instantiator<T> {
  private final Class<T> instantiatableClass;

  private DependencyInjector<T> injector;

  /**
   * Constructs a new instance that's able to create instances of the specified class.
   * @param instantiatableClass the class that will be dependency detected and instantiated
   */
  public DependencyInjectionInstantiator(Class<T> instantiatableClass) {
    this.instantiatableClass = instantiatableClass;
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
    List<DependencyDetector<T>> detectors = createDetectors(instantiatableClass);

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
      throw new DependencyDetectionException(instantiatableClass);
    }

    return immediateDependencies;
  }

  @Override
  public Class<T> getInstantiatedClass() {
    return instantiatableClass;
  }
}
