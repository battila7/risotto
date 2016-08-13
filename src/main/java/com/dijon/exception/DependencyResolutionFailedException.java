package com.dijon.exception;

import com.dijon.dependency.Dependency;

public class DependencyResolutionFailedException extends Exception {
  private static final String MESSAGE = "Dependency resolution failed for dependency: ";

  private final Dependency<?> dependency;

  public DependencyResolutionFailedException(Dependency<?> dependency) {
    super(MESSAGE + dependency.toString());

    this.dependency = dependency;
  }

  public Dependency<?> getDependency() {
    return dependency;
  }
}
