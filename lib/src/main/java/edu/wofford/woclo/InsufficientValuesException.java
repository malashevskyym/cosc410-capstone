package edu.wofford.woclo;

public class InsufficientValuesException extends RuntimeException {
  private String value;

  public InsufficientValuesException(String value) {
    super("the argument " + value + " is required");
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
