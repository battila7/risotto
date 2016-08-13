package com.dijon.exception;

import com.dijon.dependency.Dependency;

public class DependencyResolutionFailedException extends Exception {
  private final Dependency<?> dependency;

  public DependencyResolutionFailedException(Dependency<?> dependency) {
    this.dependency = dependency;
  }

  @Override
  public String getMessage() {
    return "Dependency resolution failed for dependency: " + dependency.toString();
  }
}
