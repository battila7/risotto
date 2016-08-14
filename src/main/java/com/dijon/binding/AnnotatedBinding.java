package com.dijon.binding;

import com.dijon.dependency.AnnotatedDependency;
import com.dijon.dependency.Dependency;

import java.lang.annotation.Annotation;

public class AnnotatedBinding<T> extends ComposableBinding<T> {
  private final Class<? extends Annotation> annotation;

  public AnnotatedBinding(Binding<T> binding, Class<? extends Annotation> annotation) {
    super(binding);

    this.annotation = annotation;
  }

  public boolean canResolve(Dependency<?> dependency) {
    if (!(dependency instanceof AnnotatedDependency)) {
      return false;
    }

    AnnotatedDependency<?> annotatedDependency = (AnnotatedDependency<?>) dependency;

    if (!(annotatedDependency.getAnnotation().equals(annotation))) {
      return false;
    }

    return dependency.getBoundedClass().isAssignableFrom(binding.getBoundedClass());
  }
}
