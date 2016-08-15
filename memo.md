Externalize dependency resolution logic from container classes
  * DependencyResolver class
  * The container has a DependencyResolverFactory field
    * LocalDependencyResolverFactory produces LocalDependencyResolver
    * TreeDependencyResolverFactory produces TreeDependencyResolver


Add configurators to container classes. Configurators can inspect the class of the container and call specific methods on an instance of the container.
A use case would be a configurator that looks for `@Child` annotations on the class and calls `addChildContainer()` based on the contents of the annotation.
Another use case would be a configurator that can add bindings to the container.

Some of these ideas are currently blocked by the fact that you cannot add a binding to a container from client code because `addBinding()` is `protected`. 
We don't want this method to be visible for the clients of the library and `package scoping` is not a good solution because the configurator/resolver classes would potentially be placed
in different packages.

Resolver could use the Stream API and parallel stream to resolve dependencies in a parallel manner.