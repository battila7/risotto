package com.dijon;

import com.dijon.exception.RootContainerUnsetException;

public final class Dijon {
  private static final Object rootContainerLockObject = new Object();

  private static Container rootContainer;

  public static Container addRootContainer(ChildSettings childSettings) {
    return rootContainer;
  }

  public static Container getRootContainer() throws RootContainerUnsetException {
    synchronized (rootContainerLockObject) {
      if (rootContainer == null) {
        throw new RootContainerUnsetException();
      }

      return rootContainer;
    }
  }

  private Dijon() {
    /*
     *  Cannot be instantiated.
     */
  }
}
