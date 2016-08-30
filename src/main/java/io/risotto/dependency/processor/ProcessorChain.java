package io.risotto.dependency.processor;

/**
 * Represents the chain of responsibility formed by {@link DependencyProcessor} implementations. Can
 * be used by dependency detectors to outsource parameter, method and field processing logic.
 */
public final class ProcessorChain {
  private static final DependencyProcessor processorChain;

  static {
    DependencyProcessor basicProcessor = new BasicProcessor();

    DependencyProcessor namedProcessor = new NamedProcessor();

    DependencyProcessor annotatedProcessor = new AnnotatedProcessor();

    DependencyProcessor unableProcessor = new UnableProcessor();

    basicProcessor.setSuccessor(namedProcessor);

    namedProcessor.setSuccessor(annotatedProcessor);

    annotatedProcessor.setSuccessor(unableProcessor);

    processorChain = basicProcessor;
  }

  /**
   * Gets the dependency processor chain (to be precise the first processor in the chain).
   * @return the dependency processor chain
   */
  public static DependencyProcessor getProcessorChain() {
    return processorChain;
  }

  private ProcessorChain() {
    /*
     * Cannot be instantiated.
     */
  }
}
