package com.dijon.configurator;

import java.util.ArrayList;
import java.util.List;

public class ConfiguratorManager {
  private static final List<Configurator> defaultConfigurators =
      new ArrayList<>();

  public static List<Configurator> getDefaultConfigurators() {
    return defaultConfigurators;
  }
}
