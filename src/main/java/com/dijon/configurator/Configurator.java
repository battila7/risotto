package com.dijon.configurator;

import com.dijon.Container;
import com.dijon.exception.ContainerConfigurationException;

public interface Configurator {
  void configure(Container containerInstance, Class<? extends Container> containerClass) throws
      ContainerConfigurationException;
}
