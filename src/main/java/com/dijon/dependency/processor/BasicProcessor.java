package com.dijon.dependency.processor;

import com.dijon.annotations.InjectSpecifier;
import com.dijon.annotations.Named;
import com.dijon.dependency.Dependency;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

class BasicProcessor extends DependencyProcessor {
  @Override
  public Optional<Dependency<?>> process(Parameter parameter) {
    if (parameter.getAnnotations().length > 0) {
      return super.process(parameter);
    }

    return Optional.of(new Dependency<>(parameter.getType()));
  }

  @Override
  public Optional<Dependency<?>> process(Method method) {
    if (method.isAnnotationPresent(Named.class)) {
      return super.process(method);
    }

    for (Annotation annotation : method.getAnnotations()) {
      Class<? extends Annotation> annotationType = annotation.annotationType();

      if (annotationType.isAnnotationPresent(InjectSpecifier.class)) {
        return super.process(method);
      }
    }

    Class<?> targetParameterType = method.getParameterTypes()[0];

    return Optional.of(new Dependency<>(targetParameterType));
  }
}
