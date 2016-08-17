package com.dijon;

import com.dijon.configurator.Configurator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ChildSettings {
  private final Class<? extends CustomContainer> containerClass;

  private String name;

  private final List<Configurator> configuratorList;

  public static ChildSettings child(Class<? extends CustomContainer> containerClass) {
    return new ChildSettings(containerClass);
  }

  private ChildSettings(Class<? extends CustomContainer> containerClass) {
    this.containerClass = containerClass;

    this.name = containerClass.getName();

    this.configuratorList = new ArrayList<>();
  }

  public ChildSettings as(String name) {
    this.name = name;

    return this;
  }

  public ChildSettings withConfigurators(Configurator... configurators) {
    Collections.addAll(configuratorList, configurators);

    return this;
  }
}
