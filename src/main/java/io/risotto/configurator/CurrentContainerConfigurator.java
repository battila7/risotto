package io.risotto.configurator;

import static io.risotto.binding.BasicBinding.bind;

import io.risotto.Container;
import io.risotto.annotations.CurrentContainer;
import io.risotto.binding.InstantiatableBinding;
import io.risotto.exception.ContainerConfigurationException;

import java.lang.reflect.Method;

/**
 * Configurator that adds a binding wrapping the currently configurated container instance. The
 * binding is an annotated binding using the {@link io.risotto.annotations.CurrentContainer}
 * annotation.
 */
public class CurrentContainerConfigurator implements Configurator {
  private static final String ADD_BINDING_METHOD_NAME = "addBinding";

  @Override
  public void configure(Container containerInstance, Class<? extends Container> containerClass)
      throws ContainerConfigurationException {
    InstantiatableBinding<Container> binding =
        bind(Container.class).withAnnotation(CurrentContainer.class).toInstance(containerInstance);

    addToContainer(containerInstance, containerClass, binding);
  }

  private void addToContainer(Container container, Class<? extends Container> containerClass,
                              InstantiatableBinding<?> binding)
      throws ContainerConfigurationException {
    try {
      Class<?> superClass = containerClass.getSuperclass();

      Method addBindingMethod =
          superClass.getDeclaredMethod(ADD_BINDING_METHOD_NAME, InstantiatableBinding.class);

      addBindingMethod.setAccessible(true);

      addBindingMethod.invoke(container, binding);
    } catch (SecurityException | IllegalArgumentException | ReflectiveOperationException e) {
      throw new ContainerConfigurationException(container, e);
    }
  }
}
