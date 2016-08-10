package com.dijon.binding;

import com.dijon.dependency.Dependency;
import com.dijon.instantiation.Instantiator;

import java.util.List;

public abstract class InstantiatableBinding<T> implements Binding<T> {
  protected final Binding<T> binding;

  protected Instantiator<T> instantiator;

  public InstantiatableBinding(Binding<T> binding) {
    this.binding = binding;
  }

  public T getInstance() {
    return instantiator.getInstance();
  }

  public void instantiate() {
    instantiator.instantiate();
  }

  public List<Dependency<?>> getImmediateDependencies() {
    return instantiator.getImmediateDependencies();
  }

  public boolean canResolve(Dependency<T> dependency) {
    return binding.canResolve(dependency);
  }

  public Class<T> getBoundedClass() {
    return binding.getBoundedClass();
  }
}
