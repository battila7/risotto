package io.risotto.dependency.processor;

import static io.risotto.reflection.ReflectionUtils.getInjectSpecifier;

import io.risotto.annotations.InjectSpecifier;
import io.risotto.dependency.AnnotatedDependency;
import io.risotto.dependency.Dependency;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

/**
 * This class produces dependencies from parameters, methods and fields on which an inject specifier
 * annotation is present. Inject specifier annotations are annotations marked with the {@link
 * InjectSpecifier} annotation. If multiple inject specifier annotations are present then it's not
 * defined which on of them will be processed.
 */
class AnnotatedProcessor extends DependencyProcessor {
  @Override
  public Optional<Dependency<?>> process(Parameter parameter) {
    Optional<Class<? extends Annotation>> annotationOptional = getInjectSpecifier(parameter);

    if (!annotationOptional.isPresent()) {
      return super.process(parameter);
    }

    return createWrappedAnnotatedDependency(parameter.getType(), annotationOptional.get());
  }

  @Override
  public Optional<Dependency<?>> process(Method method) {
    Optional<Class<? extends Annotation>> annotationOptional = getInjectSpecifier(method);

    if (!annotationOptional.isPresent()) {
      return super.process(method);
    }

    return createWrappedAnnotatedDependency(method.getParameterTypes()[0],
        annotationOptional.get());
  }

  @Override
  public Optional<Dependency<?>> process(Field field) {
    Optional<Class<? extends Annotation>> annotationOptional = getInjectSpecifier(field);

    if (!annotationOptional.isPresent()) {
      return super.process(field);
    }

    return createWrappedAnnotatedDependency(field.getType(), annotationOptional.get());
  }

  private Optional<Dependency<?>> createWrappedAnnotatedDependency(Class<?> boundedClass,
                                                                   Class<? extends Annotation> annotationClass) {
    return Optional.of(new AnnotatedDependency<>(boundedClass, annotationClass));
  }
}
