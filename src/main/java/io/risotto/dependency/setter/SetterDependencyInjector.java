package io.risotto.dependency.setter;

import io.risotto.dependency.Dependency;
import io.risotto.dependency.DependencyInjector;
import io.risotto.exception.InstantiationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * {@code SetterDependencyInjector} performs dependency injection through the setter methods
 * detected by an appropriate {@link SetterDependencyDetector}.
 * @param <T> the target type of dependency injection
 */
public class SetterDependencyInjector<T> extends DependencyInjector<T> {
  private static final Logger logger = LoggerFactory.getLogger(SetterDependencyInjector.class);

  private final Map<Method, Dependency<?>> methodMap;

  /**
   * Constructs a new injector that will invoke the specified setter methods passing the
   * corresponding dependencies as parameters.
   * @param clazz the class on which the injection should be performed
   * @param methodMap a map with setter methods and dependencies that should be passed to them
   */
  public SetterDependencyInjector(Class<T> clazz, Map<Method, Dependency<?>> methodMap) {
    super(clazz);

    this.methodMap = methodMap;
  }

  @Override
  public T createInstance() {
    logger.debug("Creating new instance of {}", instantiatableClass);

    try {
      Constructor<T> defaultConstructor = instantiatableClass.getConstructor();

      defaultConstructor.setAccessible(true);

      T instance = defaultConstructor.newInstance();

      for (Map.Entry<Method, Dependency<?>> pair : methodMap.entrySet()) {
        Method method = pair.getKey();

        Dependency<?> dependency = pair.getValue();

        method.invoke(instance, dependency.getResolvingBinding().getInstance());
      }

      return instance;
    } catch (SecurityException | ReflectiveOperationException | IllegalArgumentException | InstantiationFailedException e) {
      throw new InstantiationFailedException(instantiatableClass, e);
    }
  }
}
