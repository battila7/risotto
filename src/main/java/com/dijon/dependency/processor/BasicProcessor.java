package com.dijon.dependency.processor;

import com.dijon.dependency.Dependency;

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
}
