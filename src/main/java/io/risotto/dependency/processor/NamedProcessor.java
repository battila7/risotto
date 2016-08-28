package io.risotto.dependency.processor;

import io.risotto.annotations.InjectSpecifier;
import io.risotto.annotations.Named;
import io.risotto.dependency.Dependency;
import io.risotto.dependency.NamedDependency;
import io.risotto.reflection.ReflectionUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

/**
 * {@code NamedProcessor} can produce named dependencies from objects annotated with the {@link
 * Named} annotation. This processor does not check for annotations marked with the {@link
 * InjectSpecifier} annotation, which implies that {@code Named} and any inject specifier annotation
 * should not be present simultaneously on the same object, because undefined behaviour may occur
 * based on the processors' place in the processor chain.
 */
class NamedProcessor extends DependencyProcessor {
  @Override
  public Optional<Dependency<?>> process(Parameter parameter) {
    Optional<Named> namedOptional =
        ReflectionUtils.getDirectlyPresentAnnotation(parameter, Named.class);

    if (!namedOptional.isPresent()) {
      return super.process(parameter);
    }

    return Optional.of(new NamedDependency<>(parameter.getType(), namedOptional.get().value()));
  }

  @Override
  public Optional<Dependency<?>> process(Method method) {
    Optional<Named> namedOptional =
        ReflectionUtils.getDirectlyPresentAnnotation(method, Named.class);

    if (!namedOptional.isPresent()) {
      return super.process(method);
    }

    Class<?> targetParameterType = method.getParameterTypes()[0];

    return Optional.of(new NamedDependency<>(targetParameterType, namedOptional.get().value()));
  }

  @Override
  public Optional<Dependency<?>> process(Field field) {
    Optional<Named> namedOptional =
        ReflectionUtils.getDirectlyPresentAnnotation(field, Named.class);

    if (!namedOptional.isPresent()) {
      return super.process(field);
    }

    Class<?> targetFieldType = field.getType();

    return Optional.of(new NamedDependency<>(targetFieldType, namedOptional.get().value()));
  }
}
