package com.dijon.dependency;

public class NamedDependency<T> extends Dependency<T> {
  private final String name;

  public NamedDependency(Class<T> clazz, String name) {
    super(clazz);

    this.name = name;
  }

  public String getName() {
    return name;
  }
}
