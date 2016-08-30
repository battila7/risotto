package io.risotto.annotations;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Marks a method or constructor as the one to be called when cloning/copying an object. If the
 * annotation is present on a method, that method should not have any arguments and must return
 * {@code Object}. However if put on a constructor, this constructor must be a {@code copy
 * constructor} ie. taking only one argument with the same type of the object.
 */
@Target({METHOD, CONSTRUCTOR})
@Retention(RUNTIME)
@Documented
public @interface Clone {
}
