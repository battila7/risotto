package com.dijon.dependency.management.constructor;

import com.dijon.dependency.Dependency;

import java.lang.reflect.Parameter;
import java.util.Optional;

class UnableProcessor extends ParameterProcessor {
  @Override
  public Optional<Dependency<?>> process(Parameter parameter) {
    return Optional.empty();
  }
}
