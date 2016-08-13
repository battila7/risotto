package com.dijon.dependency.management.constructor;

import com.dijon.annotations.InjectSpecifier;
import com.dijon.dependency.AnnotatedDependency;
import com.dijon.dependency.Dependency;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Optional;

class AnnotatedParameterProcessor extends ParameterProcessor {
  @Override
  public Optional<Dependency<?>> process(Parameter parameter) {
    for (Annotation annotation : parameter.getAnnotations()) {
      Class<? extends Annotation> annotationType = annotation.annotationType();

      if (annotationType.isAnnotationPresent(InjectSpecifier.class)) {
        AnnotatedDependency<?> annotatedDependency =
            new AnnotatedDependency<>(parameter.getType(), annotationType);

        return Optional.of(annotatedDependency);
      }
    }

    return super.process(parameter);
  }
}
