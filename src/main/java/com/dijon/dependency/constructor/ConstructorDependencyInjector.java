package com.dijon.dependency.constructor;

import com.dijon.dependency.Dependency;
import com.dijon.dependency.DependencyInjector;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
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
    ArrayList<Object> injectableDependencies = new ArrayList<>();

    for (Dependency dependency : dependencies) {
      injectableDependencies.add(dependency.getResolvingBinding().getInstance());
    }

    return injectableConstructor.newInstance(injectableDependencies.toArray());
  }
}
