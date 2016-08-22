package io.risotto.dependency.constructor;

import io.risotto.dependency.Dependency;
import io.risotto.dependency.DependencyInjector;
import io.risotto.exception.InstantiationFailedException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Injector implementation that can inject resolved dependencies into a specific constructor method.
 * Created by the {@link ConstructorDependencyDetector} class.
 * @param <T> the target type of dependency injection
 */
public class ConstructorDependencyInjector<T> extends DependencyInjector<T> {
  private final Constructor<T> injectableConstructor;

  private final List<Dependency<?>> dependencies;

  /**
   * Constructs a new injector that will inject the specified resolved dependencies into the
   * specified constructor method of the class.
   * @param clazz the class on which the injection should be performed
   * @param dependencies the dependencies to inject
   * @param injectableConstructor the constructor to be used to perform dependency injection
   */
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
