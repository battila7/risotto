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

public abstract class InstantiatableBinding<T> extends Binding<T> {
  protected final Binding<T> binding;

  protected Instantiator<? extends T> instantiator;

  private Class<? extends Scope> scopeClass;

  private Scope scope;

  public InstantiatableBinding(Binding<T> binding) {
    super(binding.getBoundedClass());

    this.binding = binding;

    this.scopeClass = PublicScope.class;
  }

  public T getInstance() {
    return instantiator.getInstance();
  }

  public List<Dependency<?>> getImmediateDependencies() {
    return instantiator.getImmediateDependencies();
  }

  public boolean canResolve(Dependency<?> dependency) {
    return scope.isVisibleTo(dependency) && binding.canResolve(dependency);
  }

  public boolean isImportAllowedTo(Container targetContainer) {
    return scope.isImportAllowedTo(targetContainer);
  }

  public InstantiatableBinding<T> withMode(InstantiationMode mode) {
    InstantiatorFactory.decorateInstantiatorForMode(instantiator, mode);

    return this;
  }

  public InstantiatableBinding<T> withScope(Class<? extends Scope> scopeClass) {
    this.scopeClass = scopeClass;

    return this;
  }

  public InstantiatableBinding<T> publicScope() {
    return this.withScope(PublicScope.class);
  }

  public InstantiatableBinding<T> protectedScope() {
    return this.withScope(ProtectedScope.class);
  }

  public InstantiatableBinding<T> privateScope() {
    return this.withScope(PrivateScope.class);
  }

  public Class<? extends Scope> getScopeClass() {
    return scopeClass;
  }

  public Scope getScope() {
    return scope;
  }

  public void setScope(Scope scope) {
    this.scope = scope;
  }
}
