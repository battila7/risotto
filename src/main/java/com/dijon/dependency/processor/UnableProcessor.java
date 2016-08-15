package com.dijon.dependency.processor;

import com.dijon.dependency.Dependency;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

class UnableProcessor extends DependencyProcessor {
  @Override
  public Optional<Dependency<?>> process(Parameter parameter) {
    return Optional.empty();
  }

  @Override
  public Optional<Dependency<?>> process(Method method) {
    return Optional.empty();
  }

  @Override
  public Optional<Dependency<?>> process(Field field) {
    return Optional.empty();
  }
}
