package com.dijon.binding;

import com.dijon.Container;
import com.dijon.binding.scope.PrivateScope;
import com.dijon.binding.scope.ProtectedScope;
import com.dijon.binding.scope.PublicScope;
import com.dijon.binding.scope.Scope;
import com.dijon.dependency.Dependency;
import com.dijon.instantiation.InstantiationMode;
import com.dijon.instantiation.Instantiator;
import com.dijon.instantiation.InstantiatorFactory;

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
