package com.dijon.dependency.processor;

import com.dijon.annotations.Named;
import com.dijon.dependency.Dependency;
import com.dijon.dependency.NamedDependency;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

class NamedProcessor extends DependencyProcessor {
  @Override
  public Optional<Dependency<?>> process(Parameter parameter) {
    if (!parameter.isAnnotationPresent(Named.class)) {
      return super.process(parameter);
    }

    Named namedAnnotation = parameter.getDeclaredAnnotation(Named.class);

    return Optional.of(new NamedDependency<>(parameter.getType(), namedAnnotation.value()));
  }

  @Override
  public Optional<Dependency<?>> process(Method method) {
    if (!method.isAnnotationPresent(Named.class)) {
      return super.process(method);
    }

    Named namedAnnotation = method.getDeclaredAnnotation(Named.class);

    Class<?> targetParameterType = method.getParameterTypes()[0];

    return Optional.of(new NamedDependency<>(targetParameterType, namedAnnotation.value()));
  }
}
