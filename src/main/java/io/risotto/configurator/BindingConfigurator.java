package io.risotto.configurator;

import static io.risotto.binding.BasicBinding.bind;
import static io.risotto.reflection.ReflectionUtils.getDirectlyPresentAnnotation;

import io.risotto.Container;
import io.risotto.annotations.BindingSupplier;
import io.risotto.annotations.Named;
import io.risotto.annotations.WithMode;
import io.risotto.annotations.WithScope;
import io.risotto.binding.BasicBinding;
import io.risotto.binding.InstantiatableBinding;
import io.risotto.binding.MethodBinding;
import io.risotto.binding.TerminableBinding;
import io.risotto.exception.ContainerConfigurationException;
import io.risotto.reflection.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * {@code BindingConfigurator} can inspect container classes and add bindings to them by detecting
 * their <i>binding supplier methods</i>.
 *
 * A binding supplier method is annotated with the {@link BindingSupplier} annotation and may be
 * annotated with other binding-related annotations such as {@link WithScope} or {@link WithMode}.
 * The return type of the method will be turned into the bound type of the resulting binding. Method
 * parameters will be dependencies of the binding. Parameter dependencies are detected the same way
 * as in the case of setter methods, so the {@link Named} annotation and inject specifier
 * annotations can be freely used.
 *
 * Furthermore, the {@code Named} and inject specifier annotations can be placed on binding supplier
 * methods too to emulate {@link BasicBinding#as(String)} and {@link
 * BasicBinding#withAnnotation(Class)}.
 *
 * An example binding supplier method:
 * <pre>
 * <code>
 *   @Binding
 *   @WithScope(PrivateScope.class)
 *   @Named("xmlMessageLoader")
 *   public MessageLoader messagePath(@MessagePath String path) {
 *     return new XmlMessageLoader(path);
 *   }
 * </code>
 * </pre>
 */
public class BindingConfigurator implements Configurator {
  private static final String ADD_BINDING_METHOD_NAME = "addBinding";

  @Override
  public void configure(Container containerInstance, Class<? extends Container> containerClass)
      throws ContainerConfigurationException {
    for (Method method : detectBindingMethods(containerClass)) {
      try {
        method.setAccessible(true);
      } catch (SecurityException e) {
        throw new ContainerConfigurationException(containerInstance, e);
      }

      InstantiatableBinding<?> binding = createBindingFromMethod(containerInstance, method);

      addToContainer(containerInstance, containerClass, binding);
    }
  }

  private InstantiatableBinding<?> createBindingFromMethod(Container container, Method method) {
    Class<?> boundClass = method.getReturnType();

    BasicBinding<?> binding = bind(boundClass);

    return mutateBinding(binding, container, method);
  }

  private InstantiatableBinding<?> mutateBinding(BasicBinding<?> binding, Container container,
                                                 Method method) {
    TerminableBinding<?> terminableBinding = toTerminableBinding(binding, method);

    InstantiatableBinding<?> instantiatableBinding =
        new MethodBinding<>(terminableBinding, container, method);

    return detectMode(detectScope(instantiatableBinding, method), method);
  }

  private InstantiatableBinding<?> detectScope(InstantiatableBinding<?> binding, Method method) {
    Optional<WithScope> scopeOptional = getDirectlyPresentAnnotation(method, WithScope.class);

    if (scopeOptional.isPresent()) {
      return binding.withScope(scopeOptional.get().value());
    }

    return binding;
  }

  private InstantiatableBinding<?> detectMode(InstantiatableBinding<?> binding, Method method) {
    Optional<WithMode> modeOptional = getDirectlyPresentAnnotation(method, WithMode.class);

    if (modeOptional.isPresent()) {
      return binding.withMode(modeOptional.get().value());
    }

    return binding;
  }

  private void addToContainer(Container container, Class<? extends Container> containerClass,
                              InstantiatableBinding<?> binding)
      throws ContainerConfigurationException {
    try {
      Class<?> superClass = containerClass.getSuperclass();

      Method addBindingMethod =
          superClass.getDeclaredMethod(ADD_BINDING_METHOD_NAME, InstantiatableBinding.class);

      addBindingMethod.setAccessible(true);

      addBindingMethod.invoke(container, binding);
    } catch (SecurityException | IllegalArgumentException | ReflectiveOperationException e) {
      throw new ContainerConfigurationException(container, e);
    }
  }

  private List<Method> detectBindingMethods(Class<? extends Container> containerClass) {
    return Arrays.stream(containerClass.getDeclaredMethods())
        .filter(m -> ReflectionUtils.isAnnotationDirectlyPresent(m, BindingSupplier.class))
        .filter(ReflectionUtils::isMethodInjectable)
        .collect(Collectors.toList());
  }

  private TerminableBinding<?> toTerminableBinding(BasicBinding<?> binding, Method method) {
    Optional<Named> namedOptional = getDirectlyPresentAnnotation(method, Named.class);

    if (namedOptional.isPresent()) {
      return binding.as(namedOptional.get().value());
    }

    Optional<Class<? extends Annotation>> injectSpecifier =
        ReflectionUtils.getInjectSpecifier(method);

    if (injectSpecifier.isPresent()) {
      return binding.withAnnotation(injectSpecifier.get());
    }

    return binding;
  }
}
