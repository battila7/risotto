package com.dijon.exception;

import com.dijon.dependency.Dependency;

public class DependencyDetectionFailedException extends Exception {
  private static final String MESSAGE = "Dependency detection failed for dependency: ";

  private final Dependency<?> dependency;

  public DependencyDetectionFailedException(Dependency<?> dependency) {
    super(MESSAGE + dependency.toString());

    this.dependency = dependency;
  }

  public Dependency<?> getDependency() {
    return dependency;
  }
}
