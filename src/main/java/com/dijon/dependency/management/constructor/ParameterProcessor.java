package com.dijon.dependency.management.constructor;

import com.dijon.dependency.Dependency;

import java.lang.reflect.Parameter;
import java.util.Optional;

abstract class ParameterProcessor {
  private ParameterProcessor successor;

  public void setSuccessor(ParameterProcessor successor) {
    this.successor = successor;
  }

  public Optional<Dependency<?>> process(Parameter parameter) {
    return successor.process(parameter);
  }
}
