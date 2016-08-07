package com.dijon.core.binding;

import com.dijon.core.dependency.Dependency;

public class AnnotatedBinding<T> extends ComposableBinding<T> {
  public boolean canResolve(Dependency<T> dependency) {
    return false;
  }
}
