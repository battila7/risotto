package io.risotto.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.risotto.binding.AnnotatedBinding;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Signals that an annotation can be used to specify the class or instance to be injected.
 *
 * @see AnnotatedBinding
 */
@Target({ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
public @interface InjectSpecifier {
}
