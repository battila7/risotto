package com.dijon.binding;

import com.dijon.dependency.AnnotatedDependency;
import com.dijon.dependency.Dependency;

import java.lang.annotation.Annotation;

public class AnnotatedBinding<T> extends TerminableBinding<T> {
  private final Class<? extends Annotation> annotation;

  public AnnotatedBinding(Class<T> clazz, Class<? extends Annotation> annotation) {
    super(clazz);

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

    return dependency.getBoundedClass().isAssignableFrom(boundedClass);
  }
}
