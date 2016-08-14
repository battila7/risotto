package com.dijon.binding;

import com.dijon.dependency.AnnotatedDependency;
import com.dijon.dependency.Dependency;

import java.lang.annotation.Annotation;
import java.util.Objects;

public class AnnotatedBinding<T> extends TerminableBinding<T> {
  private final Class<? extends Annotation> annotationClass;

  public AnnotatedBinding(Class<T> clazz, Class<? extends Annotation> annotationClass) {
    super(clazz);

    this.annotationClass =
        Objects.requireNonNull(annotationClass, "The annotation class must not be null!");
  }

  public boolean canResolve(Dependency<?> dependency) {
    if (!(dependency instanceof AnnotatedDependency)) {
      return false;
    }

    AnnotatedDependency<?> annotatedDependency = (AnnotatedDependency<?>) dependency;

    if (!(annotatedDependency.getAnnotation().equals(annotationClass))) {
      return false;
    }

    return dependency.getBoundedClass().isAssignableFrom(boundedClass);
  }
}
