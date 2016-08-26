package io.risotto.configurator;

import static io.risotto.binding.BasicBinding.bind;

import io.risotto.Container;
import io.risotto.annotations.Binding;
import io.risotto.annotations.Named;
import io.risotto.annotations.WithMode;
import io.risotto.annotations.WithScope;
import io.risotto.binding.BasicBinding;
import io.risotto.binding.InstantiatableBinding;
import io.risotto.binding.MethodBinding;
import io.risotto.binding.TerminableBinding;
import io.risotto.exception.ContainerConfigurationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import reflection.ReflectionUtils;

public class BindingConfigurator implements Configurator {
  @Override
  public void configure(Container containerInstance, Class<? extends Container> containerClass)
      throws ContainerConfigurationException {
    for (Method method : detectBindingMethods(containerClass)) {
      try {
        method.setAccessible(true);
      } catch (SecurityException e) {
        throw new ContainerConfigurationException(containerInstance, e);
      }

      InstantiatableBinding<?> binding = createBindingFromMethod(containerInstance, method);

      addToContainer(containerInstance, containerClass, binding);
    }
  }

  private InstantiatableBinding<?> createBindingFromMethod(Container container, Method method) {
    Class<?> boundClass = method.getReturnType();

    BasicBinding<?> binding = bind(boundClass);

    return mutateBinding(binding, container, method);
  }

  private InstantiatableBinding<?> mutateBinding(BasicBinding<?> binding, Container container,
                                                 Method method) {
    TerminableBinding<?> terminableBinding = toTerminableBinding(binding, method);

    InstantiatableBinding<?>
        instantiatableBinding =
        new MethodBinding<>(terminableBinding, container, method);

    instantiatableBinding = detectBindingScope(instantiatableBinding, method);

    instantiatableBinding = detectBindingMode(instantiatableBinding, method);

    return instantiatableBinding;
  }

  private InstantiatableBinding<?> detectBindingScope(
      InstantiatableBinding<?> instantiatableBinding,
      Method method) {
    if (method.isAnnotationPresent(WithScope.class)) {
      WithScope withScope = method.getDeclaredAnnotation(WithScope.class);

      return instantiatableBinding.withScope(withScope.value());
    }

    return instantiatableBinding;
  }

  private InstantiatableBinding<?> detectBindingMode(InstantiatableBinding<?> instantiatableBinding,
                                                     Method method) {
    if (method.isAnnotationPresent(WithMode.class)) {
      WithMode withMode = method.getDeclaredAnnotation(WithMode.class);

      return instantiatableBinding.withMode(withMode.value());
    }

    return instantiatableBinding;
  }

  private void addToContainer(Container container, Class<? extends Container> containerClass,
                              InstantiatableBinding<?> binding)
      throws ContainerConfigurationException {
    try {
      Class<?> superClass = containerClass.getSuperclass();

      Method addBindingMethod =
          superClass.getDeclaredMethod("addBinding", InstantiatableBinding.class);

      addBindingMethod.setAccessible(true);

      addBindingMethod.invoke(container, binding);
    } catch (Exception e) {
      throw new ContainerConfigurationException(container, e);
    }
  }

  private List<Method> detectBindingMethods(Class<? extends Container> containerClass) {
    return Arrays.stream(containerClass.getDeclaredMethods())
        .filter(m -> m.isAnnotationPresent(Binding.class))
        .filter(ReflectionUtils::isMethodInjectable)
        .collect(Collectors.toList());
  }

  private TerminableBinding<?> toTerminableBinding(BasicBinding<?> binding, Method method) {
    if (method.isAnnotationPresent(Named.class)) {
      return binding.as(method.getDeclaredAnnotation(Named.class).value());
    }

    Optional<Class<? extends Annotation>> injectSpecifier =
        ReflectionUtils.getInjectSpecifier(method);

    if (injectSpecifier.isPresent()) {
      return binding.withAnnotation(injectSpecifier.get());
    }

    return binding;
  }
}
