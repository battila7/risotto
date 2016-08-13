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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    AnnotatedDependency<?> that = (AnnotatedDependency<?>) o;

    return annotation.equals(that.annotation);

  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + annotation.hashCode();
    return result;
  }
}
