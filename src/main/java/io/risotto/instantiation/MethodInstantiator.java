package io.risotto.instantiation;

import io.risotto.Container;
import io.risotto.binding.InstantiatableBinding;
import io.risotto.dependency.Dependency;
import io.risotto.dependency.DependencyDetector;
import io.risotto.dependency.DependencyInjector;
import io.risotto.dependency.processor.DependencyProcessor;
import io.risotto.dependency.processor.ProcessorChain;
import io.risotto.exception.DependencyDetectionException;
import io.risotto.exception.InstantiationFailedException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MethodInstantiator<T> implements Instantiator<T> {
  private final Class<T> instantiatedClass;

  private final Container container;

  private final Method method;

  private DependencyInjector<T> injector;

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
    MethodDependencyDetector<T>
        detector =
        new MethodDependencyDetector<T>(instantiatedClass, method);

    Optional<List<Dependency<?>>> dependencies = detector.detectImmediateDependencies();

    if (!dependencies.isPresent()) {
      throw new DependencyDetectionException(instantiatedClass);
    }

    injector =
        new MethodDependencyInjector<>(instantiatedClass, dependencies.get(), container, method);

    return dependencies.get();
  }

  @Override
  public Class<T> getInstantiatedClass() {
    return instantiatedClass;
  }

  private class MethodDependencyDetector<K> extends DependencyDetector<K> {
    private final Method method;

    public MethodDependencyDetector(Class<K> clazz, Method method) {
      super(clazz);

      this.method = method;
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

  private class MethodDependencyInjector<T> extends DependencyInjector<T> {
    private final List<Dependency<?>> dependencies;

    private final Container container;

    private final Method method;

    public MethodDependencyInjector(Class<T> instantiatableClass,
                                    List<Dependency<?>> dependencies,
                                    Container container,
                                    Method method) {
      super(instantiatableClass);

      this.dependencies = dependencies;

      this.container = container;

      this.method = method;
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

        return (T)instance;
      } catch (Exception e) {
        throw new InstantiationFailedException(instantiatableClass, e);
      }
    }
  }
}
