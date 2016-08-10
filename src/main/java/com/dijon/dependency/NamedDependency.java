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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    NamedDependency<?> that = (NamedDependency<?>) o;

    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + name.hashCode();
    return result;
  }
}
