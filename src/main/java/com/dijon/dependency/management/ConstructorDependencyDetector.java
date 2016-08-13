package com.dijon.dependency.management;

import com.dijon.annotations.Inject;
import com.dijon.annotations.InjectSpecifier;
import com.dijon.annotations.Named;
import com.dijon.dependency.AnnotatedDependency;
import com.dijon.dependency.Dependency;
import com.dijon.dependency.NamedDependency;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ConstructorDependencyDetector<T> extends DependencyDetector<T> {
  public ConstructorDependencyDetector(Class<T> clazz) {
    super(clazz);
  }

  @Override
  public Optional<List<Dependency<?>>> detectImmediateDependencies() {
    Optional<Constructor<T>> injectableConstructorOptional;

    try {
       injectableConstructorOptional = getInjectableConstructor();
    } catch (Exception e) {
      return Optional.empty();
    }

    if (!injectableConstructorOptional.isPresent()) {
      return Optional.empty();
    }

    Constructor<T> injectableConstructor = injectableConstructorOptional.get();

    List<Dependency<?>> dependencies = processParameters(injectableConstructor);

    this.dependencyInjector =
        new ConstructorDependencyInjector<>(clazz, dependencies, injectableConstructor);

    return Optional.of(dependencies);
  }

  private Optional<Constructor<T>> getInjectableConstructor() throws Exception {
    Constructor<?>[] constructors = clazz.getConstructors();

    if (constructors.length != 1) {
      return Optional.empty();
    }

    Constructor<?> targetConstructor = constructors[0];

    if (targetConstructor.isAnnotationPresent(Inject.class)) {
      // retrieve Constructor<T> instead of Constructor<?>
      Constructor<T> typedConstructor = clazz.getConstructor(targetConstructor.getParameterTypes());

      return Optional.of(typedConstructor);
    }

    return Optional.empty();
  }

  private List<Dependency<?>> processParameters(Constructor<?> constructor) {
    Parameter[] parameters = constructor.getParameters();

    List<Dependency<?>> dependencies = new LinkedList<>();

    for (Parameter param : parameters) {
      Annotation[] annotations = param.getAnnotations();

      if (annotations.length == 0) {
        dependencies.add(new Dependency<>(param.getType()));
      } else {
        if (param.isAnnotationPresent(Named.class)) {
          Named namedAnnotation = param.getAnnotation(Named.class);

          NamedDependency<?> dependency =
              new NamedDependency<>(param.getType(), namedAnnotation.value());

          dependencies.add(dependency);
        } else {
          for (Annotation annotation : annotations) {
            Class<? extends Annotation> annotationType =
                annotation.annotationType();

            if (annotationType.isAnnotationPresent(InjectSpecifier.class)) {
              AnnotatedDependency<?> dependency =
                  new AnnotatedDependency<>(param.getType(), annotations[0].annotationType());

              dependencies.add(dependency);

              break;
            }
          }
        }
      }
    }

    return dependencies;
  }
}
