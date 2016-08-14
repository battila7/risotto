package com.dijon.binding;

import com.dijon.dependency.Dependency;
import com.dijon.instantiation.InstantiationMode;
import com.dijon.instantiation.Instantiator;
import com.dijon.instantiation.InstantiatorFactory;

import java.util.List;

public abstract class InstantiatableBinding<T> extends Binding<T> {
  protected final Binding<T> binding;

  protected Instantiator<? extends T> instantiator;

  public InstantiatableBinding(Binding<T> binding) {
    super(binding.getBoundedClass());

    this.binding = binding;
  }

  public T getInstance() throws Exception {
    return instantiator.getInstance();
  }

  public List<Dependency<?>> getImmediateDependencies() {
    return instantiator.getImmediateDependencies();
  }

  public boolean canResolve(Dependency<?> dependency) {
    return binding.canResolve(dependency);
  }

  public InstantiatableBinding<T> withMode(InstantiationMode mode) {
    InstantiatorFactory.decorateInstantiatorForMode(instantiator, mode);

    return this;
  }
}
