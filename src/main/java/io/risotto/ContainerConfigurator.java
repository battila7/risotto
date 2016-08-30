package io.risotto;

import io.risotto.configurator.Configurator;
import io.risotto.configurator.ConfiguratorManager;
import io.risotto.exception.ContainerConfigurationException;
import io.risotto.exception.ContainerInstantiationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * Class responsible for instantiating and configuring a container. The container tree is built up
 * using {@code ContainerConfigurator} instances.
 */
final class ContainerConfigurator {
  private static final Logger logger = LoggerFactory.getLogger(ContainerConfigurator.class);

  private final ContainerSettings containerSettings;

  private final Container parentContainer;

  private Container instance;

  /**
   * Constructs a new instance that will be used to add a new container to the specified parent
   * container with the specified settings.
   * @param containerSettings the settings of the container to be configured
   * @param parentContainer the parent of the new container
   */
  public ContainerConfigurator(ContainerSettings containerSettings,
                               Container parentContainer) {
    this.containerSettings = containerSettings;

    this.parentContainer = parentContainer;
  }

  /**
   * Creates a new instance of the container class held in the settings object. Must be called prior
   * {@link #configureContainer()}.
   * @return a new container instance
   * @throws ContainerInstantiationException if the instantiation fails
   */
  public Container instantiateContainer() throws ContainerInstantiationException {
    Class<? extends Container> containerClass = containerSettings.getContainerClass();

    logger.info("Instantiating container class: {} with name: {}", containerClass,
        containerSettings.getName());

    try {
      Constructor<? extends Container> defaultConstructor = containerClass.getConstructor();

      defaultConstructor.setAccessible(true);

      instance = defaultConstructor.newInstance();

      return instance;
    } catch (SecurityException | ReflectiveOperationException | IllegalArgumentException e) {
      throw new ContainerInstantiationException(containerClass, e);
    }
  }

  /**
   * Configures the container instantiated with {@link #instantiateContainer()}. First calls the
   * {@link Container#configure()} method. Then calls the {@code Configurator}s assigned to the
   * container with {@link ContainerSettings#withConfigurators(Configurator...)}. As the last step
   * the default
   * @throws ContainerInstantiationException if a child container could not been instantiated
   * @throws ContainerConfigurationException if a child container could not been configured
   * @{code Configurator}s are called.
   *
   * If all {@code Configurator}s execute successfully the child containers of the configured
   * container will be configured.
   */
  public void configureContainer()
      throws ContainerInstantiationException, ContainerConfigurationException {
    logger.info("Configuring container: {}", containerSettings.getName());

    instance.setParentContainer(parentContainer);

    instance.setName(containerSettings.getName());

    logger.debug("Calling configure() method of container class.");

    instance.configure();

    callConfigurators();

    configureChildren();
  }

  private void callConfigurators() throws ContainerConfigurationException {
    for (Configurator configurator : ConfiguratorManager.getDefaultConfigurators()) {
      logger.debug("Calling configurator: {}", configurator);

      configurator.configure(instance, containerSettings.getContainerClass());
    }

    for (Configurator configurator : containerSettings.getConfiguratorList()) {
      logger.debug("Calling configurator: {}", configurator);

      configurator.configure(instance, containerSettings.getContainerClass());
    }
  }

  private void configureChildren()
      throws ContainerInstantiationException, ContainerConfigurationException {
    Map<String, Container> childMap = instance.getChildContainerMap();

    for (ContainerSettings settings : instance.getConfigurableChildList()) {
      ContainerConfigurator childConfigurator = new ContainerConfigurator(settings, instance);

      String name = settings.getName();

      logger.debug("Initiating configuration process for child: {}", name);

      Container childInstance = childConfigurator.instantiateContainer();

      instance.importBindingsTo(childInstance);

      childMap.put(name, childInstance);

      childConfigurator.configureContainer();
    }
  }
}
