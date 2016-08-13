package com.dijon.dependency.management.constructor;

import com.dijon.annotations.Named;
import com.dijon.dependency.Dependency;
import com.dijon.dependency.NamedDependency;

import java.lang.reflect.Parameter;
import java.util.Optional;

class NamedParameterProcessor extends ParameterProcessor {
  @Override
  public Optional<Dependency<?>> process(Parameter parameter) {
    if (!parameter.isAnnotationPresent(Named.class)) {
      return super.process(parameter);
    }

    Named namedAnnotation = parameter.getDeclaredAnnotation(Named.class);

    return Optional.of(new NamedDependency<>(parameter.getType(), namedAnnotation.value()));
  }
}
