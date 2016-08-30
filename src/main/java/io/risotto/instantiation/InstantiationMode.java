package io.risotto.instantiation;

import io.risotto.binding.InstanceBinding;
import io.risotto.binding.InstantiatableBinding;

/**
 * Enumeration of the instantiation modes that can be used for bindings.
 * @see InstantiatableBinding#withMode(InstantiationMode)
 */
public enum InstantiationMode {
  /**
   * There is only one instance in the container (there might be multiple instances of the same
   * class in other containers) that will be used to resolve all appropriate dependencies.
   */
  SINGLETON,

  /**
   * A new instance is created for every dependency that's dependant on the class in question.
   *
   * <b>Note</b> that this mode cannot be applied to {@link InstanceBinding}.
   */
  INSTANCE,

  /**
   * Only one instance is created in the container that will be used as a prototype to create clones
   * for every dependency.
   */
  PROTOTYPE
}
