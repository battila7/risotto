package com.dijon.configurator;

import com.dijon.CustomContainer;

public interface Configurator {
  void configure(CustomContainer container, Class<? extends CustomContainer> containerClass);
}
