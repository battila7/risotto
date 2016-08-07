package com.dijon.core.binding;

import com.dijon.core.dependency.Dependency;

import java.lang.annotation.Annotation;

public class BasicBinding<T> extends ComposableBinding<T> {
  public boolean canResolve(Dependency<T> dependency) {
    return false;
  }

  public NamedBinding<T> as(String name) {
    return null;
  }

  public AnnotatedBinding<T> withAnnotation(Class<? extends Annotation> annotation) {
    return null;
  }
}
