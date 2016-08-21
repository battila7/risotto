package io.risotto.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Containing annotation type for {@link Child} annotations.
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface Children {
  Child[] value();
}
