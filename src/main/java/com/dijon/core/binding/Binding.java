package com.dijon.core.binding;

import java.lang.annotation.Annotation;

public class Binding<T> extends AbstractBinding<T> {
  public NamedBinding<T> as(String name) {
    return null;
  }

  public AnnotatedBinding<T> withAnnotation(Class<? extends Annotation> annotation) {
    return null;
  }
}
