package com.dijon.configurator;

import com.dijon.CustomContainer;

public interface Configurator {
  void configure(CustomContainer containerInstance, Class<? extends CustomContainer> containerClass);
}
