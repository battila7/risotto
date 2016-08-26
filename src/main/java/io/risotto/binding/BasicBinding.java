package io.risotto.binding;

import io.risotto.dependency.Dependency;

import java.lang.annotation.Annotation;

/**
 * {@code BasicBinding} represents a binding that's not named neither annotated. This is the
 * simplest implementation that can be used to create a binding.
 * @param <T> the bound type
 */
public class BasicBinding<T> extends TerminableBinding<T> {
  /**
   * Simple static factory method to make binding creation more readable. Constructs a new {@code
   * BasicBinding} of the specified class.
   * @param bindableClass the class to bind
   * @param <T> the bound type
   * @return a new {@code BasicBinding} instance
   */
  public static <T> BasicBinding<T> bind(Class<T> bindableClass) {
    return new BasicBinding<>(bindableClass);
  }

  private BasicBinding(Class<T> boundClass) {
    super(boundClass);
  }

  /**
   * Returns whether the binding can resolve the specified dependency. Returns {@code true} if the
   * class bound by this binding is a <b>subclass</b> of the class held in the dependency, {@code false}
   * otherwise.
   * @param dependency the dependency to resolve
   * @return whether the binding can resolve the dependency
   */
  @Override
  public boolean canResolve(Dependency<?> dependency) {
    if (dependency.getClass() != Dependency.class) {
      return false;
    }

    return dependency.getBoundedClass().isAssignableFrom(boundedClass);
  }

  /**
   * Constructs a new {@code NamedBinding} with the specified name and the same bound class as the
   * current {@code BasicBinding}.
   * @param name the name of the binding
   * @return a new {@code NamedBinding} with the specified name
   */
  public NamedBinding<T> as(String name) {
    return new NamedBinding<>(boundedClass, name);
  }

  /**
   * Constructs a new {@code AnnotatedBinding} with the specified annotation class and the same
   * bound class as the current {@code BasicBinding}.
   * @param annotationClass the annotation class to be associated with the binding
   * @return a new {@code AnnotatedBinding} with the specified annotation class
   */
  public AnnotatedBinding<T> withAnnotation(Class<? extends Annotation> annotationClass) {
    return new AnnotatedBinding<>(boundedClass, annotationClass);
  }
}
