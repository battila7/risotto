package com.dijon;

import com.dijon.configurator.Configurator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ContainerSettings {
  private final Class<? extends Container> containerClass;

  private final List<Configurator> configuratorList;

  private String name;

  public static ContainerSettings container(Class<? extends Container> containerClass) {
    return new ContainerSettings(containerClass);
  }

  private ContainerSettings(Class<? extends Container> containerClass) {
    this.containerClass = containerClass;

    this.name = containerClass.getName();

    this.configuratorList = new ArrayList<>();
  }

  public ContainerSettings as(String name) {
    this.name = name;

    return this;
  }

  public ContainerSettings withConfigurators(Configurator... configurators) {
    Collections.addAll(configuratorList, configurators);

    return this;
  }

  public Class<? extends Container> getContainerClass() {
    return containerClass;
  }

  public List<Configurator> getConfiguratorList() {
    return configuratorList;
  }

  public String getName() {
    return name;
  }
}
