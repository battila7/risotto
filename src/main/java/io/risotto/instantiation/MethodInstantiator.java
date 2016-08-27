package io.risotto.instantiation;

import io.risotto.Container;
import io.risotto.annotations.BindingSupplier;
import io.risotto.dependency.Dependency;
import io.risotto.dependency.DependencyDetector;
import io.risotto.dependency.DependencyInjector;
import io.risotto.dependency.processor.DependencyProcessor;
import io.risotto.dependency.processor.ProcessorChain;
import io.risotto.exception.DependencyDetectionException;
import io.risotto.exception.InstantiationFailedException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Instantiator that uses a binding supplier method to create a new instance. Binding supplier
 * methods are methods declared in {@link Container} classes and annotated with the {@link
 * BindingSupplier} annotation. By convention binding supplier methods should return a new instance
 * of their return type on each invocation. Method parameters are detected and injected as
 * dependencies.
 * @param <T> the type of the object to be instantiated
 */
public class MethodInstantiator<T> implements Instantiator<T> {
  private final Class<T> instantiatedClass;

  private final Container container;

  private final Method method;

  private DependencyInjector<T> injector;

  /**
   * Constructs a new {@code MethodInstantiator} that prodcues instances of the specified class by
   * calling the passed {@code Method} on the passed {@code Container}.
   * @param instantiatedClass the class of the class that will be supplied by the method
   * @param container the {@code Container} in which the used method was declared
   * @param method the method that will supply the instance
   */
  public MethodInstantiator(Class<T> instantiatedClass, Container container, Method method) {
    this.instantiatedClass = instantiatedClass;

    this.container = container;

    this.method = method;
  }

  @Override
  public T getInstance() {
    return injector.createInstance();
  }

  @Override
  public Instantiator<T> getBaseInstantiator() {
    return this;
  }

  @Override
  public List<Dependency<?>> getImmediateDependencies() {
    MethodDependencyDetector detector =
        new MethodDependencyDetector(instantiatedClass);

    Optional<List<Dependency<?>>> dependencies = detector.detectImmediateDependencies();

    if (!dependencies.isPresent()) {
      throw new DependencyDetectionException(instantiatedClass);
    }

    injector =
        new MethodDependencyInjector(instantiatedClass, dependencies.get());

    return dependencies.get();
  }

  @Override
  public Class<T> getInstantiatedClass() {
    return instantiatedClass;
  }

  private class MethodDependencyDetector extends DependencyDetector<T> {
    MethodDependencyDetector(Class<T> clazz) {
      super(clazz);
    }

    @Override
    public Optional<List<Dependency<?>>> detectImmediateDependencies() {
      DependencyProcessor processorChain = ProcessorChain.getProcessorChain();

      List<Dependency<?>> dependencyList = new ArrayList<>();

      for (Parameter parameter : method.getParameters()) {
        Optional<Dependency<?>> dependencyOptional = processorChain.process(parameter);

        if (!dependencyOptional.isPresent()) {
          return Optional.empty();
        }

        dependencyList.add(dependencyOptional.get());
      }

      return Optional.of(dependencyList);
    }
  }

  private class MethodDependencyInjector extends DependencyInjector<T> {
    private final List<Dependency<?>> dependencies;

    MethodDependencyInjector(Class<T> instantiatableClass,
                             List<Dependency<?>> dependencies) {
      super(instantiatableClass);

      this.dependencies = dependencies;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T createInstance() {
      try {
        List<Object> bindingResults = new ArrayList<>();

        for (Dependency<?> dependency : dependencies) {
          bindingResults.add(dependency.getResolvingBinding().getInstance());
        }

        Object instance = method.invoke(container, bindingResults.toArray());

        return (T) instance;
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException |
          NullPointerException e) {
        throw new InstantiationFailedException(instantiatableClass, e);
      }
    }
  }
}
