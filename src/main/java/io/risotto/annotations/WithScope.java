package io.risotto.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.risotto.binding.scope.Scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Marker annotation that can be used to emulate the behaviour of {@link
 * io.risotto.binding.InstantiatableBinding#withScope(Class)} (InstantiationMode)} on binding
 * supplier methods.
 */
@Target(METHOD)
@Retention(RUNTIME)
@Documented
public @interface WithScope {
  Class<? extends Scope> value();
}
