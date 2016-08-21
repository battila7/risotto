package com.dijon.configurator;

import static com.dijon.ContainerSettings.container;

import com.dijon.Container;
import com.dijon.ContainerSettings;
import com.dijon.annotations.Child;
import com.dijon.exception.ContainerConfigurationException;

import java.lang.reflect.Method;

public class ChildConfigurator implements Configurator {
  private static final String EMPTY_STRING = "";

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

      Method addChildMethod = superclass.getDeclaredMethod("addChild", ContainerSettings.class);

      addChildMethod.setAccessible(true);

      return addChildMethod;
    } catch (Exception e) {
      throw new ContainerConfigurationException(containerInstance, e);
    }
  }
}
