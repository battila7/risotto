package io.risotto.dependency.setter;

import io.risotto.dependency.Dependency;
import io.risotto.dependency.DependencyInjector;
import io.risotto.exception.InstantiationFailedException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class SetterDependencyInjector<T> extends DependencyInjector<T> {
  private final Map<Method, Dependency<?>> methodMap;

  public SetterDependencyInjector(Class<T> clazz, Map<Method, Dependency<?>> methodMap) {
    super(clazz);

    this.methodMap = methodMap;
  }

  @Override
  public T createInstance() {
    try {
      T instance = clazz.newInstance();

      for (Map.Entry<Method, Dependency<?>> pair : methodMap.entrySet()) {
        Method method = pair.getKey();

        Dependency<?> dependency = pair.getValue();

        method.invoke(instance, dependency.getResolvingBinding().getInstance());
      }

      return instance;
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | InstantiationFailedException e) {
      throw new InstantiationFailedException(e);
    }
  }
}
