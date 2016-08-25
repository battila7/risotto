package io.risotto.instantiation;

import io.risotto.dependency.Dependency;

import java.util.List;

/**
 * Abstract base calss for object-instantiator classes. {@code Instantiator}s can be used to create and
 * retrieve instances of a specific type.
 * @param <T> the type of the objects to be instantiated
 */
public abstract class Instantiator<T> {
  protected final Class<T> instantiatableClass;

  /**
   * Constructs a new instantiator which will supply objects of the specified class.
   * @param instantiatableClass the class of which object will be created
   */
  public Instantiator(Class<T> instantiatableClass) {
    this.instantiatableClass = instantiatableClass;
  }

  /**
   * Gets an instance created by the {@code Instantiator}.
   * @return an instance
   */
  public abstract T getInstance();

  /**
   * Gets the actual {@code Instantiator} used by the current {@code Instantiator}. When there is
   * no base instantiator, {@code null} should be returned.
   * @return the base instantiator of the current instance
   */
  public abstract Instantiator<T> getBaseInstantiator();

  /**
   * Gets the list of {@code Dependency} objects that should be resolved before object
   * instantiation can take place. Only immediate dependencies are returned.
   * @return the list of immediate dependencies
   */
  public abstract List<Dependency<?>> getImmediateDependencies();

  /**
   * Gets the instantiatable class.
   * @return the instantiatable class
   */
  public Class<T> getInstantiatableClass() {
    return instantiatableClass;
  }
}
