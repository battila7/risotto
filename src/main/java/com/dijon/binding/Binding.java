package com.dijon.binding;

import com.dijon.dependency.Dependency;

public interface Binding<T> {
  boolean canResolve(Dependency<?> dependency);

  Class<T> getBoundedClass();
}
