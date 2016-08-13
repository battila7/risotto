package com.dijon;

public final class Dijon {
  private static RootContainer rootContainer;

  static {
    rootContainer = new RootContainer();
  }

  public static RootContainer getRootContainer() {
    return rootContainer;
  }

  private Dijon() {
    /*
     *  Cannot be instantiated.
     */
  }
}
