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
    Optional<String> name = getName(parameter);

    if (!name.isPresent()) {
      return super.process(parameter);
    }

    return Optional.of(new NamedDependency<>(parameter.getType(), name.get()));
  }

  @Override
  public Optional<Dependency<?>> process(Method method) {
    Optional<String> name = getName(method);

    if (!name.isPresent()) {
      return super.process(method);
    }

    return Optional.of(new NamedDependency<>(method.getParameterTypes()[0], name.get()));
  }

  @Override
  public Optional<Dependency<?>> process(Field field) {
    Optional<String> name = getName(field);

    if (!name.isPresent()) {
      return super.process(field);
    }

    return Optional.of(new NamedDependency<>(field.getType(), name.get()));
  }

  private Optional<String> getName(AnnotatedElement element) {
    Optional<Named> namedOptional =
        ReflectionUtils.getDirectlyPresentAnnotation(element, Named.class);

    if (!namedOptional.isPresent()) {
      return Optional.empty();
    }

    return Optional.of(namedOptional.get().value());
  }
}
