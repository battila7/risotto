package io.risotto.dependency.processor;

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

  public static DependencyProcessor getProcessorChain() {
    return processorChain;
  }

  private ProcessorChain() {
    /*
     * Cannot be instantiated.
     */
  }
}
