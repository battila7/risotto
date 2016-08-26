package io.risotto.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.risotto.Container;
import io.risotto.ContainerSettings;
import io.risotto.configurator.ChildConfigurator;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Signals that a child container should be added to the container on which the annotation is
 * present. The annotation is processed by the {@code ChildConfigurator} and can be used instead of
 * {@link Container#addChild(ContainerSettings)}.
 * @see ChildConfigurator
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@Repeatable(Children.class)
public @interface Child {
  /**
   * The class of the container to be added as a child.
   * @return the container class
   */
  Class<? extends Container> containerClass();

  /**
   * The name of the child container.
   * @return the name
   */
  String name() default "";
}
