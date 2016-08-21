package io.risotto.configurator;

import io.risotto.Container;
import io.risotto.exception.ContainerConfigurationException;

public interface Configurator {
  void configure(Container containerInstance, Class<? extends Container> containerClass) throws
      ContainerConfigurationException;
}
