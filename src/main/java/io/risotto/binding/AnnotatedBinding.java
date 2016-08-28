package io.risotto.binding;

import io.risotto.annotations.InjectSpecifier;
import io.risotto.dependency.AnnotatedDependency;
import io.risotto.dependency.Dependency;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * {@code AnnotatedBinding} represents a binding that uses an inject specifier annotation to resolve
 * dependencies. Inject specifier annotations are annotation classes marked with the {@link
 * InjectSpecifier} annotation. {@code AnnotatedBinding}s are used to resolve {@link
 * AnnotatedDependency} objects.
 * @param <T> the bound type
 */
public class AnnotatedBinding<T> extends TerminableBinding<T> {
  private final Class<? extends Annotation> annotationClass;

  /**
   * Constructs a new binding, binding to the specified class with the specified inject specifier
   * annotation class.
   * @param boundClass the bound class
   * @param annotationClass the associated annotation class
   * @throws NullPointerException if a parameter is {@code null}
   */
  public AnnotatedBinding(Class<T> boundClass, Class<? extends Annotation> annotationClass) {
    super(boundClass);

    this.annotationClass =
        Objects.requireNonNull(annotationClass, "The annotation class must not be null!");
  }

  /**
   * Returns whether the binding can resolve the specified dependency. Returns {@code true} if the
   * class bound by this binding is a <b>subclass</b> of the class held in the dependency and the
   * annotation class referred by the dependency is <b>equal to</b> the annotation class associated
   * with the binding.
   * @param dependency the dependency to resolve
   * @return whether the binding can resolve the dependency
   */
  public boolean canResolve(Dependency<?> dependency) {
    if (!(dependency instanceof AnnotatedDependency)) {
      return false;
    }

    AnnotatedDependency<?> annotatedDependency = (AnnotatedDependency<?>) dependency;

    return annotatedDependency.getAnnotation().equals(annotationClass)
        && annotatedDependency.getBoundedClass().isAssignableFrom(boundedClass);
  }

  /**
   * Gets the assocaited annotation class.
   * @return the annotation class
   */
  public Class<? extends Annotation> getAnnotationClass() {
    return annotationClass;
  }
}
