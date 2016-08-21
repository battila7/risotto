package io.risotto.dependency.processor;

import io.risotto.annotations.InjectSpecifier;
import io.risotto.annotations.Named;
import io.risotto.dependency.Dependency;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

class BasicProcessor extends DependencyProcessor {
  @Override
  public Optional<Dependency<?>> process(Parameter parameter) {
    if (shouldPassProcessing(parameter)) {
      return super.process(parameter);
    }

    return Optional.of(new Dependency<>(parameter.getType()));
  }

  @Override
  public Optional<Dependency<?>> process(Method method) {
    if (shouldPassProcessing(method)) {
      return super.process(method);
    }

    Class<?> targetParameterType = method.getParameterTypes()[0];

    return Optional.of(new Dependency<>(targetParameterType));
  }

  @Override
  public Optional<Dependency<?>> process(Field field) {
    if (shouldPassProcessing(field)) {
      return super.process(field);
    }

    Class<?> targetFieldType = field.getType();

    return Optional.of(new Dependency<>(targetFieldType));
  }

  private boolean shouldPassProcessing(AnnotatedElement element) {
    if (element.isAnnotationPresent(Named.class)) {
      return true;
    }

    for (Annotation annotation : element.getAnnotations()) {
      Class<? extends Annotation> annotationType = annotation.annotationType();

      if (annotationType.isAnnotationPresent(InjectSpecifier.class)) {
        return true;
      }
    }

    return false;
  }
}
