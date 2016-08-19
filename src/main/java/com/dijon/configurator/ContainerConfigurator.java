package com.dijon.configurator;

import com.dijon.Container;
import com.dijon.exception.ContainerInstantiationException;

public class ContainerConfigurator {
  private final Class<? extends Container> containerClass;

  public ContainerConfigurator(Class<? extends Container> containerClass) {
    this.containerClass = containerClass;
  }

  public Class<? extends Container> getContainerClass() {
    return containerClass;
  }

  public Container getContainerInstance() throws ContainerInstantiationException {
    try {
      Container newContainer = containerClass.newInstance();



      return newContainer;
    } catch (IllegalAccessException | InstantiationException e) {
      throw new ContainerInstantiationException(containerClass, e);
    }
  }
}
