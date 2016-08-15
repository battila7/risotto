package com.dijon.dependency.field;

import com.dijon.annotations.Inject;
import com.dijon.dependency.Dependency;
import com.dijon.dependency.DependencyDetector;
import com.dijon.dependency.processor.DependencyProcessor;
import com.dijon.dependency.processor.ProcessorChain;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FieldDependencyDetector<T> extends DependencyDetector<T> {
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
