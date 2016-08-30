package io.risotto.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.risotto.configurator.BindingConfigurator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Marker annotation for <i>binding supplier</i> methods in container implementations. For more
 * information on binding supplier methods, see {@link BindingConfigurator}.
 */
@Target(METHOD)
@Retention(RUNTIME)
@Documented
public @interface BindingSupplier {
}
