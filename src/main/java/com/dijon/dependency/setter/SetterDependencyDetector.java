package com.dijon.dependency.setter;

import static java.lang.reflect.Modifier.isAbstract;
import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;

import com.dijon.annotations.Inject;
import com.dijon.dependency.Dependency;
import com.dijon.dependency.DependencyDetector;
import com.dijon.dependency.processor.DependencyProcessor;
import com.dijon.dependency.processor.ProcessorChain;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SetterDependencyDetector<T> extends DependencyDetector<T> {
  public SetterDependencyDetector(Class<T> clazz) {
    super(clazz);
  }

  @Override
  public Optional<List<Dependency<?>>> detectImmediateDependencies() {
    DependencyProcessor processorChain = ProcessorChain.getProcessorChain();

    Map<Method, Dependency<?>> methodMap = new HashMap<>();

    List<Dependency<?>> immediateDependencies = new ArrayList<>();

    List<Method> injectableMethods = getInjectableMethods();

    if (injectableMethods.size() == 0) {
      return Optional.empty();
    }

    for (Method method : injectableMethods) {
      Optional<Dependency<?>> dependencyOptional = processorChain.process(method);

      if (!dependencyOptional.isPresent()) {
        return Optional.empty();
      }

      methodMap.put(method, dependencyOptional.get());

      immediateDependencies.add(dependencyOptional.get());
    }

    dependencyInjector = new SetterDependencyInjector<>(clazz, methodMap);

    return Optional.of(immediateDependencies);
  }

  private List<Method> getInjectableMethods() {
    return Arrays.stream(clazz.getDeclaredMethods())
        .filter(m -> m.isAnnotationPresent(Inject.class))
        .filter(m -> m.getParameterCount() == 1)
        .filter(m -> m.getName().startsWith("set"))
        .filter(m -> isMethodInjectable(m))
        .collect(Collectors.toList());
  }

  private boolean isMethodInjectable(Method method) {
    int modifiers = method.getModifiers();

    if (isStatic(modifiers)) {
      return false;
    }

    return (isPublic(modifiers)) && (!isAbstract(modifiers));
  }
}
