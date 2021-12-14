package edu.wofford.woclo;

public class HelpException extends RuntimeException {
  String helpMessage;

  public HelpException(String message) {
    super(message);
    helpMessage = message;
  }

  public String getMessage() {
    return helpMessage;
  }
}
