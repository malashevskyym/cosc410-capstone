package edu.wofford.woclo;

public class ExcessiveValuesException extends RuntimeException {
  private String value;

  public ExcessiveValuesException(String value) {
    super("the value " + value + " matches no argument");
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
