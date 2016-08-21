package io.risotto.dependency;

public abstract class DependencyInjector<T> {
  protected final Class<T> clazz;

  public DependencyInjector(Class<T> clazz) {
    this.clazz = clazz;
  }

  public abstract T createInstance();
}
