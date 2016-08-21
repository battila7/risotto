package com.dijon.dependency.constructor;

import com.dijon.dependency.Dependency;
import com.dijon.dependency.DependencyInjector;
import com.dijon.exception.InstantiationFailedException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ConstructorDependencyInjector<T> extends DependencyInjector<T> {
  private final Constructor<T> injectableConstructor;

  private final List<Dependency<?>> dependencies;

  public ConstructorDependencyInjector(Class<T> clazz, List<Dependency<?>> dependencies,
                                       Constructor<T> injectableConstructor) {
    super(clazz);

    this.dependencies = dependencies;

    this.injectableConstructor = injectableConstructor;
  }

  @Override
  public T createInstance() {
    ArrayList<Object> injectableDependencies = new ArrayList<>();

    try {
      for (Dependency dependency : dependencies) {
        injectableDependencies.add(dependency.getResolvingBinding().getInstance());
      }

      injectableConstructor.setAccessible(true);

      return injectableConstructor.newInstance(injectableDependencies.toArray());
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException
        | InstantiationFailedException | SecurityException e) {
      throw new InstantiationFailedException(e);
    }
  }
}
