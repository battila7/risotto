package io.risotto.dependency;

import io.risotto.annotations.Inject;
import io.risotto.dependency.constructor.ConstructorDependencyDetector;
import io.risotto.dependency.field.FieldDependencyDetector;
import io.risotto.dependency.setter.SetterDependencyDetector;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Base class for dependency detectors that inspect a class (using reflection) and recognize its
 * immediate dependencies. Currently there are three strategies implemented: constructor, setter
 * method and field-based dependency detection. All of these strategies are driven by the {@link
 * Inject} annotation.
 *
 * Detectors must be able to return an injector that can perform dependency injection (and therefore
 * instantiation) on the class on which dependency detection took place.
 * @param <T> the type to dependency detect
 */
public abstract class DependencyDetector<T> {
  protected final Class<T> clazz;

  protected DependencyInjector<T> dependencyInjector;

  /**
   * Creates a list populated with different dependency detector implementations for the specified
   * class. This list includes the currently supported strategies and can be used like a chain of
   * responsibility.
   * @param clazz the dependency detectable class
   * @param <C> the type to dependency detect
   * @return a list of dependency detectors
   */
  public static <C> List<DependencyDetector<C>> createDetectors(Class<C> clazz) {
    return Arrays
        .asList(new ConstructorDependencyDetector<>(clazz), new SetterDependencyDetector<>(clazz),
            new FieldDependencyDetector<>(clazz));
  }

  /**
   * Constructs a new instance that will be used to detect the dependencies of the specified class.
   * @param clazz the dependency detectable class
   */
  public DependencyDetector(Class<T> clazz) {
    this.clazz = clazz;
  }

  /**
   * Detects and returns the list of immediate dependencies of the type in the class' type
   * parameter. If there are no dependencies, an empty list should be returned. Upon failure the
   * returned object must be an empty {@code Optional}.
   * @return an {@code Optional} that either wraps the list of immediate dependencies or is empty
   */
  public abstract Optional<List<Dependency<?>>> detectImmediateDependencies();

  /**
   * Gets the injector that can inject the dependencies detected by this detector. Must be called
   * after {@link #detectImmediateDependencies()}.
   * @return the injector with the appropriate injection an instantiation logic
   * @throws IllegalStateException if {@link #detectImmediateDependencies()} has not been called yet
   * or returned an empty {@code Optional}
   */
  public DependencyInjector<T> getInjector() {
    if (dependencyInjector == null) {
      throw new IllegalStateException(
          "Dependency detection is not yet performed or could not create injector!");
    }

    return this.dependencyInjector;
  }
}
