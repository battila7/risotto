package com.dijon.binding;

import com.dijon.dependency.Dependency;

import java.lang.annotation.Annotation;

public class BasicBinding<T> implements Binding<T> {
  private final Class<T> clazz;

  public static <T> BasicBinding<T> bind(Class<T> clazz) {
   return new BasicBinding<T>(clazz);
  }

  private BasicBinding(Class<T> clazz) {
    this.clazz = clazz;
  }

  public Class<T> getBoundedClass() {
    return clazz;
  }

  public boolean canResolve(Dependency<?> dependency) {
    return clazz.isAssignableFrom(dependency.getBoundedClass());
  }

  public NamedBinding<T> as(String name) {
    return null;
  }

  public AnnotatedBinding<T> withAnnotation(Class<? extends Annotation> annotation) {
    return null;
  }
}
