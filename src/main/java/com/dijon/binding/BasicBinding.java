package com.dijon.binding;

import com.dijon.dependency.Dependency;

import java.lang.annotation.Annotation;

public class BasicBinding<T> extends TerminableBinding<T> {
  public static <T> BasicBinding<T> bind(Class<T> clazz) {
    return new BasicBinding<>(clazz);
  }

  private BasicBinding(Class<T> clazz) {
    super(clazz);
  }

  @Override
  public boolean canResolve(Dependency<?> dependency) {
    if (dependency.getClass() != Dependency.class) {
      return false;
    }

    return dependency.getBoundedClass().isAssignableFrom(boundedClass);
  }

  public NamedBinding<T> as(String name) {
    return new NamedBinding<>(boundedClass, name);
  }

  public AnnotatedBinding<T> withAnnotation(Class<? extends Annotation> annotation) {
    return new AnnotatedBinding<>(boundedClass, annotation);
  }
}
