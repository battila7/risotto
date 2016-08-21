package com.dijon;

import com.dijon.binding.InstantiatableBinding;
import com.dijon.configurator.Configurator;
import com.dijon.exception.ContainerInstantiationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ContainerConfigurator {
  private static final List<Configurator> defaultConfiguratorList = new ArrayList<>();

  private final ContainerSettings containerSettings;

  private final Container parentContainer;

  private Container instance;

  public static void registerDefaultConfigurator(Configurator configurator) {
    defaultConfiguratorList.add(configurator);
  }

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

  public void configureContainer() throws ContainerInstantiationException {
    instance.setParentContainer(parentContainer);

    instance.configure();

    callConfigurators();

    configureChildren();
  }

  private void callConfigurators() {
    defaultConfiguratorList.stream()
        .forEach(c -> c.configure(instance, containerSettings.getContainerClass()));

    containerSettings.getConfiguratorList().stream()
        .forEach(c -> c.configure(instance, containerSettings.getContainerClass()));
  }

  private void configureChildren() throws ContainerInstantiationException {
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
