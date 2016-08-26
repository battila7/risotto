package io.risotto.instantiation;

/**
 * Abstract base class that offers common functionality for classes that can be used for prototype
 * cloning.
 * @param <T> the type of the object to be cloned
 */
abstract class PrototypeCloner<T> {
  protected final Class<? extends T> cloneableClass;

  /**
   * Constructs a new instance that will be used to clone instances of the specified class.
   * @param cloneableClass the class of the object to be cloned
   */
  public PrototypeCloner(Class<? extends T> cloneableClass) {
    this.cloneableClass = cloneableClass;
  }

  /**
   * Returns whether the cloner is able to produce clones of the class it's been created with.
   * @return whether the cloner can create clones
   */
  public abstract boolean canClone();

  /**
   * Creates clones of the specified prototype object.
   * @param prototype the prototype that will be used to produce a clone
   * @return a new clone
   */
  public abstract T createClone(T prototype);
}
