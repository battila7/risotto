package com.dijon.exception;

import com.dijon.dependency.Dependency;

public class DependencyDetectionFailedException extends Exception {
  private final Dependency<?> dependency;

  public DependencyDetectionFailedException(Dependency<?> dependency) {
    this.dependency = dependency;
  }

  @Override
  public String getMessage() {
    return "Dependency detection failed for dependency: " + dependency.toString();
  }
}
