package com.dijon.dependency.processor;

import com.dijon.annotations.InjectSpecifier;
import com.dijon.dependency.AnnotatedDependency;
import com.dijon.dependency.Dependency;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

class AnnotatedProcessor extends DependencyProcessor {
  @Override
  public Optional<Dependency<?>> process(Parameter parameter) {
    Optional<Class<? extends Annotation>> annotationOptional = getInjectSpecifier(parameter);

    if (annotationOptional.isPresent()) {
      AnnotatedDependency<?> annotatedDependency =
          new AnnotatedDependency<>(parameter.getType(), annotationOptional.get());

      return Optional.of(annotatedDependency);
    }

    return super.process(parameter);
  }

  @Override
  public Optional<Dependency<?>> process(Method method) {
    Optional<Class<? extends Annotation>> annotationOptional = getInjectSpecifier(method);

    if (annotationOptional.isPresent()) {
      Class<?> targetParameterType = method.getParameterTypes()[0];

      AnnotatedDependency<?> annotatedDependency =
          new AnnotatedDependency<>(targetParameterType, annotationOptional.get());

      return Optional.of(annotatedDependency);
    }

    return super.process(method);
  }

  @Override
  public Optional<Dependency<?>> process(Field field) {
    Optional<Class<? extends Annotation>> annotationOptional = getInjectSpecifier(field);

    if (annotationOptional.isPresent()) {
      Class<?> targetFieldType = field.getType();

      AnnotatedDependency<?> annotatedDependency =
          new AnnotatedDependency<>(targetFieldType, annotationOptional.get());

      return Optional.of(annotatedDependency);
    }

    return super.process(field);
  }

  private Optional<Class<? extends Annotation>> getInjectSpecifier(AnnotatedElement element) {
    for (Annotation annotation : element.getAnnotations()) {
      Class<? extends Annotation> annotationType = annotation.annotationType();

      if (annotationType.isAnnotationPresent(InjectSpecifier.class)) {
        return Optional.of(annotationType);
      }
    }

    return Optional.empty();
  }
}
