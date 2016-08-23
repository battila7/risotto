package io.risotto.dependency.setter;

import static java.lang.reflect.Modifier.isAbstract;
import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;

import io.risotto.annotations.Inject;
import io.risotto.dependency.Dependency;
import io.risotto.dependency.DependencyDetector;
import io.risotto.dependency.processor.DependencyProcessor;
import io.risotto.dependency.processor.ProcessorChain;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Detector implementation that inspects <b>public</b> setter methods of a class and looks for the
 * {@link Inject} annotation on them. If setter inspection succeeds, a new {@link
 * SetterDependencyInjector} is created.
 *
 * Only <b>public</b> methods having name starting with {@code set} and having one parameter
 * are considered as a target to dependency injection. Note that <b>static</b> or <b>abstract</b>
 * methods are ignored.
 * @param <T> the type to dependency detect
 */
public class SetterDependencyDetector<T> extends DependencyDetector<T> {
  /**
   * Constructs a new instance that will be used to detect the dependencies of the specified class.
   * @param clazz the dependency detectable class
   */
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
