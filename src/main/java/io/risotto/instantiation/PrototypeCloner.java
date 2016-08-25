package io.risotto.instantiation;

abstract class PrototypeCloner<T> {
  protected final Class<? extends T> cloneableClass;

  public PrototypeCloner(Class<? extends T> cloneableClass) {
    this.cloneableClass = cloneableClass;
  }

  public abstract boolean canClone();

  public abstract T createClone(T prototype);
}
