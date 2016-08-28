package io.risotto.dependency;

import io.risotto.annotations.Named;
import io.risotto.binding.NamedBinding;

/**
 * {@code NamedDependency} is used to represent dependencies marked with the {@link Named}
 * annotation.
 * <p>
 * A specific instance of {@code NamedDependency} can only be resolved by {@link NamedBinding}
 * objects binding a child type of the type represented by the {@code NamedDependency} and
 * containing the same name as the {@code NamedDependency} object in question.
 * <p>
 * For more information on dependencies, read the documentation of {@link Dependency}.
 * @param <T> the type represented by the dependency
 */
public class NamedDependency<T> extends Dependency<T> {
  private final String name;

  /**
   * Creates a new instance that represents a <b>named</b> dependency on the specified class.
   * @param boundedClass the class another class is dependant on
   * @param name the name for the dependency (the {@code value} of the {@code Named} annotation)
   */
  public NamedDependency(Class<T> boundedClass, String name) {
    super(boundedClass);

    this.name = name;
  }

  /**
   * Gets the name of the dependency.
   * @return the name
   */
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
