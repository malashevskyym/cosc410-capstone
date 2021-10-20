package edu.wofford.woclo;

public class HelpException extends RuntimeException {
  @Override
  public Throwable fillInStackTrace() {
    return this;
  }

  public HelpException(String errorMessage) {
    super(errorMessage);
  }
}
