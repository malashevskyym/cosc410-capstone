package edu.wofford.woclo;

public class IllegalTypeException extends RuntimeException {
  private String type;
  private String value;

  public IllegalTypeException(String value, String type) {
    super("the value " + value + " is not of type " + type);

    this.value = value;
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public String getValue() {
    return value;
  }
}
