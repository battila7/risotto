package com.dijon.core.binding;

import com.dijon.core.dependency.Dependency;

import java.lang.annotation.Annotation;

public class AnnotatedBinding<T> extends ComposableBinding<T> {
  private final Class<? extends Annotation> annotation;

  public AnnotatedBinding(Binding<T> binding, Class<? extends Annotation> annotation) {
    super(binding);

    this.annotation = annotation;
  }

  public boolean canResolve(Dependency<T> dependency) {
    return false;
  }
}
