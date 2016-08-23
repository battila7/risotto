package io.risotto.configurator;

import static io.risotto.ContainerSettings.container;

import io.risotto.Container;
import io.risotto.ContainerSettings;
import io.risotto.annotations.Child;
import io.risotto.exception.ContainerConfigurationException;

import java.lang.reflect.Method;

/**
 * Configurator that can add child containers to a container using {@link Child} annotations. Uses
 * {@link Container#addChild(ContainerSettings)} under the hood.
 */
public class ChildConfigurator implements Configurator {
  private static final String EMPTY_STRING = "";

  private static final String ADD_CHILD_METHOD_NAME = "addChild";

  @Override
  public void configure(Container containerInstance, Class<? extends Container> containerClass)
      throws ContainerConfigurationException {
    Child[] annotations = containerClass.getAnnotationsByType(Child.class);

    Method addChildMethod = getAddChildMethod(containerInstance, containerClass);

    for (Child annotation : annotations) {
      addChildContainer(containerInstance, addChildMethod, annotation);
    }
  }

  private void addChildContainer(Container containerInstance, Method addChildMethod,
                                 Child annotation) throws ContainerConfigurationException {
    ContainerSettings childSettings = container(annotation.containerClass());

    if (!annotation.name().equals(EMPTY_STRING)) {
      childSettings.as(annotation.name());
    }

    try {
      addChildMethod.invoke(containerInstance, childSettings);
    } catch (Exception e) {
      throw new ContainerConfigurationException(containerInstance, e);
    }
  }

  private Method getAddChildMethod(Container containerInstance,
                                   Class<? extends Container> containerClass)
      throws ContainerConfigurationException {
    try {
      Class<?> superclass = containerClass.getSuperclass();

      Method addChildMethod =
          superclass.getDeclaredMethod(ADD_CHILD_METHOD_NAME, ContainerSettings.class);

      addChildMethod.setAccessible(true);

      return addChildMethod;
    } catch (Exception e) {
      throw new ContainerConfigurationException(containerInstance, e);
    }
  }
}
