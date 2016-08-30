package io.risotto.dependency.field;

import io.risotto.dependency.Dependency;
import io.risotto.dependency.DependencyInjector;
import io.risotto.exception.InstantiationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * {@code FieldDependencyInjection} can set the fields of an object to the appropriate dependencies
 * of the class detected by the corresponding {@link FieldDependencyDetector}.
 * @param <T> the target type of dependency injection
 */
public class FieldDependencyInjector<T> extends DependencyInjector<T> {
  private static final Logger logger = LoggerFactory.getLogger(FieldDependencyInjector.class);

  private final Map<Field, Dependency<?>> fieldMap;

  /**
   * Constructs a new injector that injects the specified dependencies to their corresponding
   * fields.
   * @param clazz the class on which the injection should be performed
   * @param fieldMap a map with fields and dependencies that should be injected into them
   */
  public FieldDependencyInjector(Class<T> clazz, Map<Field, Dependency<?>> fieldMap) {
    super(clazz);

    this.fieldMap = fieldMap;
  }

  @Override
  public T createInstance() {
    logger.debug("Creating new instance of {}", instantiatableClass);

    try {
      Constructor<T> defaultConstructor = instantiatableClass.getConstructor();

      defaultConstructor.setAccessible(true);

      T instance = defaultConstructor.newInstance();

      for (Map.Entry<Field, Dependency<?>> pair : fieldMap.entrySet()) {
        Field field = pair.getKey();

        field.setAccessible(true);

        Dependency<?> dependency = pair.getValue();

        field.set(instance, dependency.getResolvingBinding().getInstance());
      }

      return instance;
    } catch (SecurityException | ReflectiveOperationException | IllegalArgumentException | InstantiationFailedException e) {
      throw new InstantiationFailedException(instantiatableClass, e);
    }
  }
}
