package com.dijon.dependency.processor;

import com.dijon.annotations.InjectSpecifier;
import com.dijon.dependency.AnnotatedDependency;
import com.dijon.dependency.Dependency;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

class AnnotatedProcessor extends DependencyProcessor {
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

  @Override
  public Optional<Dependency<?>> process(Method method) {
    for (Annotation annotation : method.getAnnotations()) {
      Class<? extends Annotation> annotationType = annotation.annotationType();

      if (annotationType.isAnnotationPresent(InjectSpecifier.class)) {
        Class<?> targetParameterType = method.getParameterTypes()[0];

        AnnotatedDependency<?> annotatedDependency =
            new AnnotatedDependency<>(targetParameterType, annotationType);

        return Optional.of(annotatedDependency);
      }
    }

    return super.process(method);
  }
}
