package io.risotto.dependency.processor;

import io.risotto.dependency.Dependency;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

public abstract class DependencyProcessor {
  private DependencyProcessor successor;

  public void setSuccessor(DependencyProcessor successor) {
    this.successor = successor;
  }

  public Optional<Dependency<?>> process(Parameter parameter) {
    return successor.process(parameter);
  }

  public Optional<Dependency<?>> process(Method method) {
    return successor.process(method);
  }

  public Optional<Dependency<?>> process(Field field) {
    return successor.process(field);
  }
}
