package com.dijon.binding;

import com.dijon.dependency.Dependency;

import java.lang.annotation.Annotation;

public class AnnotatedBinding<T> extends ComposableBinding<T> {
  private final Class<? extends Annotation> annotation;

  public AnnotatedBinding(Binding<T> binding, Class<? extends Annotation> annotation) {
    super(binding);

    this.annotation = annotation;
  }

  public boolean canResolve(Dependency<?> dependency) {
    return false;
  }
}
