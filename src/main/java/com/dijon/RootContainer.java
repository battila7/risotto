package com.dijon;

import com.dijon.binding.InstantiatableBinding;
import com.dijon.dependency.Dependency;

import java.util.Optional;

/**
 * Created by Attila on 2016. 08. 11..
 */
public class RootContainer extends AbstractContainer {
  @Override
  protected Optional<InstantiatableBinding<?>> resolve(Dependency<?> dependency) {
    return Optional.empty();
  }
}
