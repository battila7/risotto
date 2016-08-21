package io.risotto.annotations;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Marks a constructor, a field or a setter method as a target for dependency injection. Risotto first
 * looks for a properly annotated constructor. If that's not found the next step is finding {@code
 * public} setter methods with this annotation. If no {@code public} setter methods are found, then
 * Risotto looks for fields with the {@code Inject} annotation.
 */
@Target({CONSTRUCTOR, FIELD, METHOD})
@Retention(RUNTIME)
@Documented
public @interface Inject {
}
