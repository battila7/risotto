package io.risotto.instantiation;

import io.risotto.dependency.Dependency;

import java.util.List;

/**
 * Interface for object-instantiator classes. {@code Instantiator}s can be used to create and
 * retrieve instances of a specific type.
 * @param <T> the type of the objects to be instantiated
 */
public interface Instantiator<T> {
  /**
   * Gets an instance created by the {@code Instantiator}.
   * @return an instance
   */
  T getInstance();

  /**
   * Gets the actual {@code Instantiator} used by the current {@code Instantiator}. When there is no
   * base instantiator, {@code null} should be returned.
   * @return the base instantiator of the current instance
   */
  Instantiator<T> getBaseInstantiator();

  /**
   * Gets the list of {@code Dependency} objects that should be resolved before object instantiation
   * can take place. Only immediate dependencies are returned.
   * @return the list of immediate dependencies
   */
  List<Dependency<?>> getImmediateDependencies();

  /**
   * Gets the class of the objects that this {@code Instantiator} produces.
   * @return the class of which instances are created
   */
  Class<T> getInstantiatedClass();
}
