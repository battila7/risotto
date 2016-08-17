package com.dijon.configurator;

import com.dijon.CustomContainer;
import com.dijon.exception.ContainerInstantiationException;

public class ContainerConfigurator {
  private final Class<? extends CustomContainer> containerClass;

  public ContainerConfigurator(Class<? extends CustomContainer> containerClass) {
    this.containerClass = containerClass;
  }

  public Class<? extends CustomContainer> getContainerClass() {
    return containerClass;
  }

  public CustomContainer getContainerInstance() throws ContainerInstantiationException {
    try {
      CustomContainer newContainer = containerClass.newInstance();

      newContainer.add

      return newContainer;
    } catch (IllegalAccessException | InstantiationException e) {
      throw new ContainerInstantiationException(containerClass, e);
    }
  }
}
