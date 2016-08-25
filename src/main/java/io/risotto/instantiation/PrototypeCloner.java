package io.risotto.instantiation;

abstract class PrototypeCloner<T> {
  protected final Class<T> cloneableClass;

  public PrototypeCloner(Class<T> cloneableClass) {
    this.cloneableClass = cloneableClass;
  }

  public abstract boolean canClone();

  public abstract T createClone(T prototype);
}
