package io.risotto.exception;

public class InvalidContainerNameException extends Exception {
  private static final String MESSAGE = "The desired container name is already in use: ";

  private final String name;

  public InvalidContainerNameException(String name) {
    super(MESSAGE + name);

    this.name = name;
  }

  public String getName() {
    return name;
  }
}
