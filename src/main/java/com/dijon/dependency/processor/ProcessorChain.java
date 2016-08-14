package com.dijon.dependency.processor;

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

  private ProcessorChain() {
    /*
     * Cannot be instantiated.
     */
  }

  public static DependencyProcessor getProcessorChain() {
    return processorChain;
  }
}
