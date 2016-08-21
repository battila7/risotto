package io.risotto;

import io.risotto.configurator.Configurator;
import io.risotto.configurator.ConfiguratorManager;
import io.risotto.exception.ContainerConfigurationException;
import io.risotto.exception.ContainerInstantiationException;

import java.util.Map;

final class ContainerConfigurator {
  private final ContainerSettings containerSettings;

  private final Container parentContainer;

  private Container instance;

  public ContainerConfigurator(ContainerSettings containerSettings,
                               Container parentContainer) {
    this.containerSettings = containerSettings;

    this.parentContainer = parentContainer;
  }

  public Container instantiateContainer() throws ContainerInstantiationException {
    Class<? extends Container> containerClass = containerSettings.getContainerClass();

    try {
      instance = containerClass.newInstance();

      return instance;
    } catch (IllegalAccessException | InstantiationException e) {
      throw new ContainerInstantiationException(containerClass, e);
    }
  }

  public void configureContainer()
      throws ContainerInstantiationException, ContainerConfigurationException {
    instance.setParentContainer(parentContainer);

    instance.configure();

    callConfigurators();

    configureChildren();
  }

  private void callConfigurators() throws ContainerConfigurationException {
    for (Configurator configurator : ConfiguratorManager.getDefaultConfigurators()) {
      configurator.configure(instance, containerSettings.getContainerClass());
    }

    for (Configurator configurator : containerSettings.getConfiguratorList()) {
      configurator.configure(instance, containerSettings.getContainerClass());
    }
  }

  private void configureChildren()
      throws ContainerInstantiationException, ContainerConfigurationException {
    Map<String, Container> childMap = instance.getChildContainerMap();

    for (ContainerSettings settings : instance.getConfigurableChildList()) {
      ContainerConfigurator childConfigurator = new ContainerConfigurator(settings, instance);

      String name = settings.getName();

      Container childInstance = childConfigurator.instantiateContainer();

      instance.importBindingsTo(childInstance);

      childMap.put(name, childInstance);

      childConfigurator.configureContainer();
    }
  }
}
