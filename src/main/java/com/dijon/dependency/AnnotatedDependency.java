package com.dijon.dependency;

import java.lang.annotation.Annotation;

public class AnnotatedDependency<T> extends Dependency<T> {
  private final Class<? extends Annotation> annotation;

  public AnnotatedDependency(Class<T> clazz, Class<? extends Annotation> annotation) {
    super(clazz);

    this.annotation = annotation;
  }

  public Class<? extends Annotation> getAnnotation() {
    return annotation;
  }
}
