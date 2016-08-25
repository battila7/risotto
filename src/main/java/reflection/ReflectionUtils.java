package reflection;

import static java.lang.reflect.Modifier.isAbstract;
import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionUtils {
  public static boolean isPublicNotStaticNotFinal(Member member) {
    int modifiers = member.getModifiers();

    return Modifier.isPublic(modifiers)
        && (!Modifier.isFinal(modifiers))
        && (!Modifier.isStatic(modifiers));
  }

  public static boolean isMethodInjectable(Method method) {
    int modifiers = method.getModifiers();

    if (isStatic(modifiers) || isAbstract(modifiers)) {
      return false;
    }

    return isPublic(modifiers);
  }


  public static boolean isFieldInjectable(Field field) {
    int modifiers = field.getModifiers();

    return !(Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers) || field.isEnumConstant());
  }

  private ReflectionUtils() {
    /**
     * Cannot be instantiated.
     */
  }
}
