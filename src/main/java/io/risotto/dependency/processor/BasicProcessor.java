package io.risotto.dependency.processor;

import static io.risotto.reflection.ReflectionUtils.getInjectSpecifier;
import static io.risotto.reflection.ReflectionUtils.isAnnotationDirectlyPresent;

import io.risotto.annotations.InjectSpecifier;
import io.risotto.annotations.Named;
import io.risotto.dependency.Dependency;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

/**
 * Processor implementation that's able to recognize basic dependencies. Basic dependencies are the
 * simplest dependencies withoout {@link Named} or {@link InjectSpecifier} marked annotations. If it
 * detects an annotation that might be useful for other processors, passes the processed object to
 * its successor in the responsibility chain.
 */
class BasicProcessor extends DependencyProcessor {
  @Override
  public Optional<Dependency<?>> process(Parameter parameter) {
    if (shouldPassProcessing(parameter)) {
      return super.process(parameter);
    }

    return Optional.of(new Dependency<>(parameter.getType()));
  }

  @Override
  public Optional<Dependency<?>> process(Method method) {
    if (shouldPassProcessing(method)) {
      return super.process(method);
    }

    return Optional.of(new Dependency<>(method.getParameterTypes()[0]));
  }

  @Override
  public Optional<Dependency<?>> process(Field field) {
    if (shouldPassProcessing(field)) {
      return super.process(field);
    }

    return Optional.of(new Dependency<>(field.getType()));
  }

  private boolean shouldPassProcessing(AnnotatedElement element) {
    return isAnnotationDirectlyPresent(element, Named.class)
        || getInjectSpecifier(element).isPresent();
  }
}
