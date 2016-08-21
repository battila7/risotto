package io.risotto.dependency.processor;

import io.risotto.annotations.Named;
import io.risotto.dependency.Dependency;
import io.risotto.dependency.NamedDependency;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

class NamedProcessor extends DependencyProcessor {
  @Override
  public Optional<Dependency<?>> process(Parameter parameter) {
    if (!parameter.isAnnotationPresent(Named.class)) {
      return super.process(parameter);
    }

    return Optional.of(new NamedDependency<>(parameter.getType(), getAnnotationValue(parameter)));
  }

  @Override
  public Optional<Dependency<?>> process(Method method) {
    if (!method.isAnnotationPresent(Named.class)) {
      return super.process(method);
    }

    Class<?> targetParameterType = method.getParameterTypes()[0];

    return Optional.of(new NamedDependency<>(targetParameterType, getAnnotationValue(method)));
  }

  @Override
  public Optional<Dependency<?>> process(Field field) {
    if (!field.isAnnotationPresent(Named.class)) {
      return super.process(field);
    }

    Class<?> targetFieldType = field.getType();

    return Optional.of(new NamedDependency<>(targetFieldType, getAnnotationValue(field)));
  }

  private String getAnnotationValue(AnnotatedElement element) {
    return element.getDeclaredAnnotation(Named.class).value();
  }
}
