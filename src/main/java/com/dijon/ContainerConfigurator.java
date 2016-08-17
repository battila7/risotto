package com.dijon;

import com.dijon.exception.ContainerInstantiationException;

final class ContainerConfigurator {
  private final ContainerSettings containerSettings;

  private final Container parentContainer;

  public ContainerConfigurator(ContainerSettings containerSettings,
                               Container parentContainer) {
    this.containerSettings = containerSettings;

    this.parentContainer = parentContainer;
  }

  public CustomContainer instantiateContainer() throws ContainerInstantiationException {
    Class<? extends CustomContainer> containerClass = containerSettings.getContainerClass();

    try {
      return containerClass.newInstance();
    } catch (IllegalAccessException | InstantiationException e) {
      throw new ContainerInstantiationException(containerClass, e);
    }
  }

  public void configureContainer() {

  }
}
