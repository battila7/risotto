package com.dijon.dependency.field;

import com.dijon.dependency.Dependency;
import com.dijon.dependency.DependencyInjector;
import com.dijon.exception.InstantiationFailedException;

import java.lang.reflect.Field;
import java.util.Map;

public class FieldDependencyInjector<T> extends DependencyInjector<T> {
  private final Map<Field, Dependency<?>> fieldMap;

  public FieldDependencyInjector(Class<T> clazz, Map<Field, Dependency<?>> fieldMap) {
    super(clazz);

    this.fieldMap = fieldMap;
  }

  @Override
  public T createInstance() {
    try {
      T instance = clazz.newInstance();

      for (Map.Entry<Field, Dependency<?>> pair : fieldMap.entrySet()) {
        Field field = pair.getKey();

        field.setAccessible(true);

        Dependency<?> dependency = pair.getValue();

        field.set(instance, dependency.getResolvingBinding().getInstance());
      }

      return instance;
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
        | SecurityException | InstantiationFailedException e) {
      throw new InstantiationFailedException(e);
    }
  }
}
