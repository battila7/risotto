package com.dijon.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Signals that an annotation can be used to specify the class or instance to be injected.
 */
@Target( { PARAMETER, FIELD, METHOD })
@Retention(RUNTIME)
@Documented
public @interface InjectSpecifier {
}
