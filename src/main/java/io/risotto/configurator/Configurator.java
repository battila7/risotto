package io.risotto.configurator;

import io.risotto.Container;
import io.risotto.exception.ContainerConfigurationException;

/**
 * Common interface for classes that can be used to customize container configuration. Implementors
 * of this interface can inspect container classes and call methods on them. This allows, for
 * example annotation-driven configuration of containers instead of overusing the {@link
 * Container#configure()} method.
 * <p>
 * Heavy use of reflection is allowed for {@code Configurator} implementations.
 */
public interface Configurator {
  /**
   * Configures the specified container instance.
   * @param containerInstance the instance of the container class
   * @param containerClass the class of the container that can be inspected
   * @throws ContainerConfigurationException if an error occurs during the configuration process
   */
  void configure(Container containerInstance, Class<? extends Container> containerClass) throws
      ContainerConfigurationException;
}
