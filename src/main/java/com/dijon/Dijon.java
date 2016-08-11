package com.dijon;

/**
 * Created by Attila on 2016. 08. 11..
 */
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
