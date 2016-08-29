package io.risotto.binding;

import io.risotto.Container;
import io.risotto.binding.scope.PrivateScope;
import io.risotto.binding.scope.ProtectedScope;
import io.risotto.binding.scope.PublicScope;
import io.risotto.binding.scope.Scope;
import io.risotto.dependency.Dependency;
import io.risotto.instantiation.InstantiationMode;
import io.risotto.instantiation.Instantiator;
import io.risotto.instantiation.InstantiatorFactory;

import java.util.List;
import java.util.Objects;

/**
 * Abstract decorator class that can be used as a basis for bindings that can be used as sources of
 * instances for dependency injection.
 * @param <T> the bound type
 */
public abstract class InstantiatableBinding<T> extends Binding<T> {
  protected final Binding<T> binding;

  protected Instantiator<? extends T> instantiator;

  private Class<? extends Scope> scopeClass;

  private Scope scope;

  /**
   * Constructs a new instance decorating the specified binding.
   * @param binding the binding to decorate
   * @throws NullPointerException if the binding is {@code null}
   */
  public InstantiatableBinding(Binding<T> binding) {
    super(binding.getBoundedClass());

    this.binding = Objects.requireNonNull(binding);

    this.scopeClass = PublicScope.class;
  }

  /**
   * Gets an instance of the binding's bound type.
   * @return an instance of the bound type
   */
  public T getInstance() {
    return instantiator.getInstance();
  }

  /**
   * Gets the immediate dependencies of the binding.
   * @return the list of immediate dependencies
   */
  public List<Dependency<?>> getImmediateDependencies() {
    return instantiator.getImmediateDependencies();
  }

  /**
   * Returns whether the binding can resolve the specified dependency. Returns {@code true} if the
   * <b>decorated</b> binding can resolve the specified dependency and the current binding is
   * <b>visible</b> to the dependency.
   * @param dependency the dependency to resolve
   * @return whether the binding can resolve the dependency
   */
  public boolean canResolve(Dependency<?> dependency) {
    return scope.isVisibleTo(dependency) && binding.canResolve(dependency);
  }

  /**
   * Returns whether the binding can be imported to the specified container.
   * @param targetContainer the container to import into
   * @return {@code true} if the binding can be imported, {@code false} otherwise
   */
  public boolean isImportAllowedTo(Container targetContainer) {
    return scope.isImportAllowedTo(targetContainer);
  }

  /**
   * Applies the specified {@code InstantiationMode} to the binding. Can be used to alter the
   * behaviour of {@link #getInstance()}.
   * @param mode the new mode
   * @return the current instance
   * @throws NullPointerException if the mode is {@code null}
   */
  public InstantiatableBinding<T> withMode(InstantiationMode mode) {
    if (mode == null) {
      throw new NullPointerException("The mode must not be null!");
    }

    this.instantiator = InstantiatorFactory.decorateInstantiatorForMode(instantiator, mode);

    return this;
  }

  /**
   * Applies the specified {@code Scope} class to the binding. Can be used to alter the behaviour of
   * {@link #isImportAllowedTo(Container)}.
   * @param scopeClass the scope to be used
   * @return the current instance
   * @throws NullPointerException if the scope class is null
   */
  public InstantiatableBinding<T> withScope(Class<? extends Scope> scopeClass) {
    this.scopeClass = Objects.requireNonNull(scopeClass);

    return this;
  }

  /**
   * Applies {@link PublicScope} to the binding
   * @return the current instance
   */
  public InstantiatableBinding<T> publicScope() {
    return this.withScope(PublicScope.class);
  }

  /**
   * Applies {@link ProtectedScope} to the binding
   * @return the current instance
   */
  public InstantiatableBinding<T> protectedScope() {
    return this.withScope(ProtectedScope.class);
  }

  /**
   * Applies {@link PrivateScope} to the binding
   * @return the current instance
   */
  public InstantiatableBinding<T> privateScope() {
    return this.withScope(PrivateScope.class);
  }

  /**
   * Gets the scope class applied to the binding.
   * @return the scope class
   */
  public Class<? extends Scope> getScopeClass() {
    return scopeClass;
  }

  /**
   * Gets the actual scope instance used by the binding.
   * @return the scope instance
   */
  public Scope getScope() {
    return scope;
  }

  /**
   * Sets the actual scope instance used by the binding.
   * @param scope the scope instance
   * @throws NullPointerException if the scope is {@code null}
   */
  public void setScope(Scope scope) {
    this.scope = Objects.requireNonNull(scope);
  }

  @Override
  public String toString() {
    return "InstantiatableBinding{" +
        "binding=" + binding +
        ", instantiator=" + instantiator +
        ", scopeClass=" + scopeClass +
        '}';
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

    InstantiatableBinding<?> that = (InstantiatableBinding<?>) o;

    if (!binding.equals(that.binding)) {
      return false;
    }
    return scopeClass.equals(that.scopeClass);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + binding.hashCode();
    result = 31 * result + scopeClass.hashCode();
    return result;
  }
}
