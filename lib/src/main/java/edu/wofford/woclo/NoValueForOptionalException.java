package edu.wofford.woclo;

public class NoValueForOptionalException extends RuntimeException {
  private String value;

  public NoValueForOptionalException(String value) {
    super("no value for " + value);
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
