package io.risotto.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.risotto.binding.NamedBinding;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Marks an injection target to be injected with the specified named instance or class. This
 * annotation can be placed on <ul> <li>constructor parameters,</li> <li>setter methods,</li>
 * <li>fields.</li> </ul>.
 * @see NamedBinding
 */
@Target({PARAMETER, FIELD, METHOD})
@Retention(RUNTIME)
@Documented
public @interface Named {
  /**
   * The name of the specific class or instance.
   * @return the name
   */
  String value();
}
