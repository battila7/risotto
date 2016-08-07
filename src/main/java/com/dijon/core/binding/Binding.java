package com.dijon.core.binding;

import com.dijon.core.dependency.Dependency;

public interface Binding<T> {
  boolean canResolve(Dependency<T> dependency);

  Class<T> getBoundedClass();
}
