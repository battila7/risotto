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
    if (dependency.getClass() != Dependency.class) {
      return false;
    }

    return clazz.isAssignableFrom(dependency.getBoundedClass());
  }

  public InstantiatableBinding<T> toClass(Class<? extends T> clazz) {
    return new ClassBinding<T>(this, clazz);
  }

  public <K extends T> InstantiatableBinding<T> toInstance(K instance) {
    return new InstanceBinding<T>(this, instance);
  }

  public NamedBinding<T> as(String name) {
    return new NamedBinding<>(this, name);
  }

  public AnnotatedBinding<T> withAnnotation(Class<? extends Annotation> annotation) {
    return new AnnotatedBinding<>(this, annotation);
  }
}
