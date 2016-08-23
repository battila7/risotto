package io.risotto.dependency.processor;

import io.risotto.dependency.Dependency;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

/**
 * Represents a processor that's unable to process anything. An instance of this class is the last
 * member of the dependency processor chain.
 */
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
