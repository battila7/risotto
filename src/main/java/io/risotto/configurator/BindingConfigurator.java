package io.risotto.configurator;

import io.risotto.Container;
import io.risotto.annotations.Binding;
import io.risotto.binding.InstantiatableBinding;
import io.risotto.exception.ContainerConfigurationException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import reflection.ReflectionUtils;

public class BindingConfigurator implements Configurator {
  @Override
  public void configure(Container containerInstance, Class<? extends Container> containerClass)
      throws ContainerConfigurationException {
    detectBindingMethods(containerClass)
        .stream()
        .map(this::createBindingFromMethod)
        .forEach(this::addToContainer);
  }

  private InstantiatableBinding<?> createBindingFromMethod(Method method) {
    return null;
  }


  private void addToContainer(InstantiatableBinding<?> binding) {

  }

  private List<Method> detectBindingMethods(Class<? extends Container> containerClass) {
    return Arrays.stream(containerClass.getDeclaredMethods())
        .filter(m -> m.isAnnotationPresent(Binding.class))
        .filter(ReflectionUtils::isMethodInjectable)
        .collect(Collectors.toList());
  }
}
