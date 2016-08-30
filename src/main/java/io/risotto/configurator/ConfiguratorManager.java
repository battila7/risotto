package io.risotto.configurator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Manager class that holds the list of default configurators. These are the configurators that will
 * be called on all containers when configuring the container tree. Custom configurators can be
 * registered using the {@link #registerDefaultConfigurator(Configurator...)} method.
 */
public class ConfiguratorManager {
  private static final Logger logger = LoggerFactory.getLogger(ConfiguratorManager.class);

  private static final List<Configurator> defaultConfiguratorList =
      new ArrayList<>();

  static {
    registerDefaultConfigurator(new ChildConfigurator(), new BindingConfigurator(),
        new CurrentContainerConfigurator());
  }

  /**
   * Registers the specified {@code Configurator}s among the default configurators.
   * @param configurators the configurators to register
   * @throws NullPointerException if a {@code null} value has been passed
   */
  public static void registerDefaultConfigurator(Configurator... configurators) {
    logger.info("Registered default configurators: {}", Arrays.toString(configurators));

    for (Configurator configurator : configurators) {
      if (configurator == null) {
        throw new NullPointerException("The configurators array must not contain null elements!");
      }
    }

    Collections.addAll(defaultConfiguratorList, configurators);
  }

  /**
   * Gets the list of default configurators.
   * @return the list of default configurators
   */
  public static List<Configurator> getDefaultConfigurators() {
    return defaultConfiguratorList;
  }
}
