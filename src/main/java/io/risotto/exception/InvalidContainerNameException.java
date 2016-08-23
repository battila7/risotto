package io.risotto.exception;

/**
 * This kind of exception occurs when a specific child container name is already assigned to another
 * child container.
 */
public class InvalidContainerNameException extends Exception {
  private static final String MESSAGE = "The desired container name is already in use: ";

  private final String name;

  /**
   * Constructs a new instance with the colliding name.
   * @param name the name that's already taken
   */
  public InvalidContainerNameException(String name) {
    super(MESSAGE + name);

    this.name = name;
  }

  /**
   * Gets the colliding name.
   * @return the name that's already taken
   */
  public String getName() {
    return name;
  }
}
