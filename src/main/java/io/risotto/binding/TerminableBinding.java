package io.risotto.binding;

/**
 * Represents a binding that can be turned into an {@link InstantiatableBinding}, therefore can
 * terminate the binding-creation chain.
 * @param <T> the bound type
 */
public abstract class TerminableBinding<T> extends Binding<T> {
  /**
   * Constructs a new instance with the specified bound class.
   * @param boundClass the bound class
   */
  public TerminableBinding(Class<T> boundClass) {
    super(boundClass);
  }

  /**
   * Creates a new {@code InstantiatableBinding} binding the specified target class to the bound
   * type.
   * @param targetClass the class to bind to the bound type
   * @return a new {@code InstantiatableBinding} with a class-class binding
   */
  public InstantiatableBinding<T> toClass(Class<? extends T> targetClass) {
    return new ClassBinding<>(this, targetClass);
  }

  /**
   * Creates a new {@code InstantiatableBinding} binding the specified target instance to the bound
   * type.
   * @param instance the instance to bind to the bound type
   * @param <K> the type of the bound instance
   * @return a new {@code InstantiatableBinding} with a class-instance binding
   */
  public <K extends T> InstantiatableBinding<T> toInstance(K instance) {
    return new InstanceBinding<>(this, instance);
  }
}
