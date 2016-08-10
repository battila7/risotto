package com.dijon.dependency.management;

import com.dijon.dependency.Dependency;

import java.lang.reflect.Constructor;
import java.util.List;

public class ConstructorDependencyInjector<T> extends DependencyInjector<T> {
  private final Constructor<T> injectableConstructor;

  public ConstructorDependencyInjector(Class<T> clazz, List<Dependency<?>> dependencies,
                                       Constructor<T> injectableConstructor) {
    super(clazz, dependencies);

    this.injectableConstructor = injectableConstructor;
  }

  @Override
  public T createInstance() throws Exception {
    Object[] params = dependencies.stream()
                                   .map(x -> x.getResolvingBinding().getInstance())
                                   .toArray();

    return injectableConstructor.newInstance(params);
  }
}
