package io.risotto.dependency.processor;

import io.risotto.dependency.Dependency;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

/**
 * Base class for processors that can produce dependencies by analyzing parameters, methods or
 * fields. There could be separate processor base classes for those three objects but inspecting
 * them is very similar that's why there's a single base class for this task. Implementors form a
 * chain of responsibility held by {@link ProcessorChain}.
 */
public abstract class DependencyProcessor {
  private DependencyProcessor successor;

  /**
   * Sets the successor of the current object in the responsibility chain.
   * @param successor the successor in the chain
   */
  public void setSuccessor(DependencyProcessor successor) {
    this.successor = successor;
  }

  /**
   * Processes a {@code Parameter} and produces a new dependency. Returns an empty {@code Optional}
   * upon failure, otherwise, a dependency object is wrapped in the returned {@code Optional}.
   * @param parameter the parameter to process
   * @return an {@code Optional} with the resulting dependency
   */
  public Optional<Dependency<?>> process(Parameter parameter) {
    return successor.process(parameter);
  }

  /**
   * Processes a {@code Method} and produces a new dependency. Returns an empty {@code Optional}
   * upon failure, otherwise, a dependency object is wrapped in the returned {@code Optional}.
   * @param method the method to process
   * @return an {@code Optional} with the resulting dependency
   */
  public Optional<Dependency<?>> process(Method method) {
    return successor.process(method);
  }

  /**
   * Processes a {@code Field} and produces a new dependency. Returns an empty {@code Optional}
   * upon failure, otherwise, a dependency object is wrapped in the returned {@code Optional}.
   * @param field the method to process
   * @return an {@code Optional} with the resulting dependency
   */
  public Optional<Dependency<?>> process(Field field) {
    return successor.process(field);
  }
}
