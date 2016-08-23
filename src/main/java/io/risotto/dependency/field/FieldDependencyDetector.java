package io.risotto.dependency.field;

import io.risotto.annotations.Inject;
import io.risotto.dependency.Dependency;
import io.risotto.dependency.DependencyDetector;
import io.risotto.dependency.processor.DependencyProcessor;
import io.risotto.dependency.processor.ProcessorChain;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Detector class that inspects the fields of a class and looks for the {@link Inject} annotation on
 * them. Public, protected, private and package scoped fields are all inspected and can be a target
 * to dependency injection. If field inspection succeeds, a new {@link FieldDependencyInjector} is
 * created.
 *
 * Each field (with the {@code Inject} annotation) becomes an immediate dependency.
 *
 * Note that <b>static</b> and/or <b>final</b> fields are not inspected.
 * @param <T> the type to dependency detect
 */
public class FieldDependencyDetector<T> extends DependencyDetector<T> {
  /**
   * Constructs a new instance that will be used to detect the dependencies of the specified class.
   * @param clazz the dependency detectable class
   */
  public FieldDependencyDetector(Class<T> clazz) {
    super(clazz);
  }

  @Override
  public Optional<List<Dependency<?>>> detectImmediateDependencies() {
    DependencyProcessor processorChain = ProcessorChain.getProcessorChain();

    Map<Field, Dependency<?>> fieldMap = new HashMap<>();

    List<Dependency<?>> immediateDependencies = new ArrayList<>();

    List<Field> injectableFields = getInjectableFields();

    if (injectableFields.size() == 0) {
      return Optional.empty();
    }

    for (Field field : injectableFields) {
      Optional<Dependency<?>> dependencyOptional = processorChain.process(field);

      if (!dependencyOptional.isPresent()) {
        return Optional.empty();
      }

      fieldMap.put(field, dependencyOptional.get());

      immediateDependencies.add(dependencyOptional.get());
    }

    dependencyInjector = new FieldDependencyInjector<>(clazz, fieldMap);

    return Optional.of(immediateDependencies);
  }

  private List<Field> getInjectableFields() {
    return Arrays.stream(clazz.getDeclaredFields())
        .filter(f -> f.isAnnotationPresent(Inject.class))
        .filter(f -> notStaticFinal(f))
        .filter(f -> !f.isEnumConstant())
        .collect(Collectors.toList());
  }

  private boolean notStaticFinal(Field field) {
    int modifiers = field.getModifiers();

    return !(Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers));
  }
}