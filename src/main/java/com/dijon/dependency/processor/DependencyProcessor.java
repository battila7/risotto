package com.dijon.dependency.processor;

import com.dijon.dependency.Dependency;

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
}
