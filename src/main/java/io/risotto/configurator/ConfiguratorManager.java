package io.risotto.configurator;

import java.util.ArrayList;
import java.util.List;

public class ConfiguratorManager {
  private static final List<Configurator> defaultConfiguratorList =
      new ArrayList<>();

  static {
    registerDefaultConfigurator(new ChildConfigurator());
  }

  public static void registerDefaultConfigurator(Configurator configurator) {
    defaultConfiguratorList.add(configurator);
  }

  public static List<Configurator> getDefaultConfigurators() {
    return defaultConfiguratorList;
  }
}
