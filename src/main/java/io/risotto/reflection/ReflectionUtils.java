package io.risotto.reflection;

import static java.lang.reflect.Modifier.isAbstract;
import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;

import io.risotto.annotations.Inject;
import io.risotto.annotations.InjectSpecifier;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Provides common reflection-related utility methods for other classes throughout the library.
 * Various checks are extracted into this class so they do not pollute other classes that have other
 * more important functionality. Only intended to contain static methods.
 */
public class ReflectionUtils {
  /**
   * Checks if the provided method <b>is</b> {@code public} and <b>not</b> {@code final} and
   * <b>not</b> {@code static}.
   * @param member the member to perform the check on
   * @return whether the member satisfies the predicates
   */
  public static boolean isPublicNotStaticNotFinal(Member member) {
    int modifiers = member.getModifiers();

    return isPublic(modifiers)
        && (!isFinal(modifiers))
        && (!isStatic(modifiers));
  }

  /**
   * Checks if a method is injectable (injection target to be precise). That means, the method
   * <b>is</b> {@code public} but <b>not</b> {@code static} and <b>not</b> {@code abstract}.
   * @param method the method to perform the check on
   * @return whether the method is injectable
   */
  public static boolean isMethodInjectable(Method method) {
    int modifiers = method.getModifiers();

    if (isStatic(modifiers) || isAbstract(modifiers)) {
      return false;
    }

    return isPublic(modifiers);
  }


  /**
   * Checks if a field is injectable (injection target). That means, the field is <b>not</b> {@code
   * static}, <b>not</b> {@code final} and <b>not</b> an {@code enum constant}.
   * @param field the field to perform the check on
   * @return whether the field is injectable
   */
  public static boolean isFieldInjectable(Field field) {
    int modifiers = field.getModifiers();

    return !(isStatic(modifiers) || isFinal(modifiers) || field.isEnumConstant());
  }

  /**
   * Gets the inject specifier annotation (if present) on the specified element. If no inject
   * specifier is present, an empty {@code Optional} is returned.
   * @param element the element to be checked for the inject specifier
   * @return an {@code Optional} that either be empty or contains the class of the inject specifier
   * @see InjectSpecifier
   */
  public static Optional<Class<? extends Annotation>> getInjectSpecifier(AnnotatedElement element) {
    for (Annotation annotation : element.getDeclaredAnnotations()) {
      Class<? extends Annotation> annotationType = annotation.annotationType();

      if (annotationType.getDeclaredAnnotation(InjectSpecifier.class) != null) {
        return Optional.of(annotationType);
      }
    }

    return Optional.empty();
  }

  /**
   * Gets an annotation that is directly present on the specified element. If no annotation with the
   * specified class is directly present, an empty {@code Optional} is returned.
   * @param element the element to be checked for the annotation
   * @param annotationClass the annotation class to be searched for
   * @param <A> the type of the annotation returned
   * @return an {@code Optional} that either be empty or contains a directly present instance of the
   * specified annotation class
   */
  public static <A extends Annotation> Optional<A> getDirectlyPresentAnnotation(
      AnnotatedElement element,
      Class<A> annotationClass) {
    return Optional.ofNullable(element.getDeclaredAnnotation(annotationClass));
  }

  /**
   * Checks whether an instance of the specified annotation class is directly present on the
   * element.
   * @param element the element to be checked for the annotation
   * @param annotationClass the annotation class to be searched for
   * @return whether the an instance of the annotation class is directly present or not
   */
  public static boolean isAnnotationDirectlyPresent(AnnotatedElement element,
                                                    Class<? extends Annotation> annotationClass) {
    return getDirectlyPresentAnnotation(element, annotationClass).isPresent();
  }

  /**
   * Checks whether the {@link Inject} annotation is directly present on the specified element.
   * Shorthand for {@code isAnnotationDirectlyPresent(element, Inject.class)}.
   * @param element the element to be checked for the {@code Inject} annotation
   * @return whether the {@code Inject} is present on the element or not.
   */
  public static boolean isInjectDirectlyPresent(AnnotatedElement element) {
    return isAnnotationDirectlyPresent(element, Inject.class);
  }

  private ReflectionUtils() {
    /**
     * Cannot be instantiated.
     */
  }
}
