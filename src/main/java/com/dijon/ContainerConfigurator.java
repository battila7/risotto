package com.dijon;

import com.dijon.exception.ContainerInstantiationException;

final class ContainerConfigurator {
  private final ChildSettings childSettings;

  private final Container parentContainer;

  public ContainerConfigurator(ChildSettings childSettings,
                               Container parentContainer) {
    this.childSettings = childSettings;

    this.parentContainer = parentContainer;
  }

  public CustomContainer instantiateContainer() throws ContainerInstantiationException {
    Class<? extends CustomContainer> containerClass = childSettings.getContainerClass();

    try {
      return containerClass.newInstance();
    } catch (IllegalAccessException | InstantiationException e) {
      throw new ContainerInstantiationException(containerClass, e);
    }
  }

  public void configureContainer() {

  }
}
