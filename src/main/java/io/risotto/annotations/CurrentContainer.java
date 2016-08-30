package io.risotto.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Inject specifier annotation that can be used to mark a target to current container instance
 * injection.
 * @see io.risotto.configurator.CurrentContainerConfigurator;
 */
@InjectSpecifier
@Target({ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
public @interface CurrentContainer {
}
