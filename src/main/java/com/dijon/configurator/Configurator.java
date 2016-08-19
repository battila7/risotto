package com.dijon.configurator;

import com.dijon.Container;

public interface Configurator {
  void configure(Container containerInstance, Class<? extends Container> containerClass);
}
