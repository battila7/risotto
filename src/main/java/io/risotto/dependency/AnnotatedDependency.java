package io.risotto.dependency;

import io.risotto.annotations.InjectSpecifier;
import io.risotto.binding.AnnotatedBinding;

import java.lang.annotation.Annotation;

/**
 * {@code AnnotatedDependency} is used to represent dependencies annotated with an inject specifier
 * annotation. These are annotation marked with the {@link InjectSpecifier} annotation.
 * <p>
 * A specific instance of {@code AnnotatedDependency} can only be resolved by {@link
 * AnnotatedBinding} objects binding a child type of the type represented by the {@code
 * AnnotatedDependency} and containing the same annotation as the {@code AnnotatedDependency} object
 * in question.
 * <p>
 * For more information on dependencies, read the documentation of {@link Dependency}.
 * @param <T> the type represented by the dependency
 */
public class AnnotatedDependency<T> extends Dependency<T> {
  private final Class<? extends Annotation> annotation;

  /**
   * Creates a new instance that represents an <b>inject specifier annotated</b> dependency on the
   * specified class.
   * @param boundedClass the class another class is dependant on
   * @param annotation the annotation used to mark the dependency
   */
  public AnnotatedDependency(Class<T> boundedClass, Class<? extends Annotation> annotation) {
    super(boundedClass);

    this.annotation = annotation;
  }

  /**
   * Gets the annotation associated with the dependency.
   * @return the associated annotation
   */
  public Class<? extends Annotation> getAnnotation() {
    return annotation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    AnnotatedDependency<?> that = (AnnotatedDependency<?>) o;

    return annotation.equals(that.annotation);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + annotation.hashCode();
    return result;
  }
}
