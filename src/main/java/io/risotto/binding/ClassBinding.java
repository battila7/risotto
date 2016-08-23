package io.risotto.binding;

import io.risotto.instantiation.DependencyInjectionInstantiator;
import io.risotto.instantiation.InstantiatorFactory;

import java.util.Objects;

/**
 * {@code ClassBinding} binds another class to its bound class. This class must be a subclass of the
 * binding's bound class. {@code ClassBindings} are the bindings that generate dependencies, because
 * target classes are dependency detected in order to be creatable.
 * @param <T> the bound type
 */
public class ClassBinding<T> extends InstantiatableBinding<T> {
  private final Class<? extends T> targetClass;

  /**
   * Constructs a new {@code ClassBinding} with the specified target class and the specified wrapped
   * binding.
   * @param binding the binding to wrap
   * @param targetClass the target class to be associated with the bound class
   */
  public ClassBinding(Binding<T> binding, Class<? extends T> targetClass) {
    super(binding);

    this.targetClass = Objects.requireNonNull(targetClass, "The target class must not be null!");

    instantiator = InstantiatorFactory
        .decorateWithDefaultInstantiator(new DependencyInjectionInstantiator<>(targetClass));
  }

  /**
   * Gets the target class (the one that'll be actually instantiated).
   * @return the target class
   */
  public Class<? extends T> getTargetClass() {
    return targetClass;
  }
}