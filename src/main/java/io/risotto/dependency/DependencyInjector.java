package io.risotto.dependency;

/**
 * Abstract base class for injectors that do the heavy-lifting of object creation and dependency
 * injection. Injectors must be created by detectors in order to keep detection and injection logic
 * separated from each other.
 * @param <T> the target type of dependency injection
 */
public abstract class DependencyInjector<T> {
  protected final Class<T> clazz;

  /**
   * Constructs a new instance that will be used to create new instances of the specified class.
   * @param clazz the class to be instantiated
   */
  public DependencyInjector(Class<T> clazz) {
    this.clazz = clazz;
  }

  /**
   * Creates and returns a new instance of the appropriate type. The returned instance contains
   * all of its dependencies.
   * @return a new instance of the type parameter
   */
  public abstract T createInstance();
}
