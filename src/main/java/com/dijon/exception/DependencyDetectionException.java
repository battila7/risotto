package com.dijon.exception;

public class DependencyDetectionException extends RuntimeException {
  private static final String MESSAGE = "Dependency detection failed for class: ";

  private final Class<?> clazz;

  public DependencyDetectionException(Class<?> clazz) {
    super(MESSAGE + clazz.toString());

    this.clazz = clazz;
  }

  public Class<?> getCausingClass() {
    return clazz;
  }
}
