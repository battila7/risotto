package io.risotto.binding;

import static io.risotto.instantiation.InstantiationMode.INSTANCE;

import io.risotto.instantiation.InstantiationMode;
import io.risotto.instantiation.InstantiatorFactory;
import io.risotto.instantiation.NoOpInstantiator;

import java.util.Objects;

/**
 * This binding binds an actual instance to its bound class. The class of the instance held in the
 * binding must be a subclass of the binding's bound class. This instance is then used to resolve
 * dependencies with appropriate types.
 * @param <T> the bound type
 */
public class InstanceBinding<T> extends InstantiatableBinding<T> {
  private final T instance;

  /**
   * Constructs a new {@code InstanceBinding} holding the specified instance and wrapping the
   * specified binding.
   * @param binding the binding to wrap
   * @param instance the associated instance
   * @param <I> the type of the instance, must be a subtype of the bound type
   * @throws NullPointerException if a parameter is {@code null}
   */
  public <I extends T> InstanceBinding(Binding<T> binding, I instance) {
    super(binding);

    this.instance = Objects.requireNonNull(instance);

    instantiator =
        InstantiatorFactory.decorateWithDefaultInstantiator(new NoOpInstantiator<>(instance));
  }

  /**
   * Applies the specified {@code InstantiationMode} to the binding. Can be used to alter the
   * behaviour of {@link #getInstance()}.
   * @param mode the new mode
   * @return the current instance
   * @throws IllegalArgumentException if the mode is {@link InstantiationMode#INSTANCE}
   */
  @Override
  public InstantiatableBinding<T> withMode(InstantiationMode mode) {
    if (mode == INSTANCE) {
      throw new IllegalArgumentException("INSTANCE mode cannot be applied to this binding!");
    }

    return super.withMode(mode);
  }

  @Override
  public String toString() {
    return "InstanceBinding{" +
        "instance=" + instance +
        "} " + super.toString();
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

    InstanceBinding<?> that = (InstanceBinding<?>) o;

    return instance.equals(that.instance);

  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + instance.hashCode();
    return result;
  }
}
