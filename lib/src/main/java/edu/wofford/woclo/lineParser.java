package edu.wofford.woclo;

import java.util.*;

public class LineParser {
  public enum Datatype {
    STRING,
    INTEGER,
    FLOAT
  };

  public String exceptionMessage = "";

  private List<Datatype> types = new ArrayList<Datatype>();
  private List<String> identifiers = new ArrayList<String>();
  private List<String> arguments = new ArrayList<String>();
  private List<Float> valueFloat = new ArrayList<Float>();
  private List<Integer> valueInt = new ArrayList<Integer>();
  private List<String> descriptions = new ArrayList<String>();
  private String programHelpMessage = "";
  private String helpMessage = "";
  private Datatype type = Datatype.STRING;

  public LineParser(int numArgs, String[] arguments) {
    this(numArgs, arguments, Datatype.STRING);
  }

  /**
   * Constructor LineParser
   *
   * <p>Creates an instance of the mainArgs String Array that contains the arguments passed in the
   * command line.
   *
   * @param arguments are the arguments that are being passed in the command line.
   */
  public LineParser(int numArgs, String[] arguments, Datatype type) {
    if (detectHelp(arguments)) {
      constructHelpMessage();
      System.out.println(helpMessage);
    }

    for (int i = 0; i < numArgs; i++) {
      addIdentifier(String.valueOf(i));
    }

    if (arguments.length > identifiers.size()) {
      exceptionMessage = "Too many arguments";
      throw new IllegalArgumentException(exceptionMessage);
    } else if (arguments.length < identifiers.size()) {
      exceptionMessage = "Too few arguments";
      throw new IllegalArgumentException(exceptionMessage);
    }
    this.arguments = Arrays.asList(arguments);
    this.type = type;
    convertType(this.arguments);
  }

  /**
   * Adds a new identifier as the default type String and without a description.
   *
   * <p>Adds an identifier without a description for the Usage message. It's type will be a String.
   *
   * @param name The name of the identifier.
   */
  public void addIdentifier(String name) {
    addIdentifier(Datatype.STRING, name, "");
  }

  /**
   * Adds a new identifier as the default type String and with the provided description.
   *
   * <p>Adds an identifier with a description for the Usage message. It's type will be a String.
   *
   * @param name The name of the identifier.
   * @param description The decription of the identifier.
   */
  public void addIdentifier(String name, String description) {
    addIdentifier(Datatype.STRING, name, description);
  }

  /**
   * Adds a new identifier as the default type String and with the provided description.
   *
   * <p>Adds an identifier with the specified type and with a description for the Usage message.
   *
   * @param type The type of the identifier.
   * @param name The name of the identifier.
   * @param description The decription of the identifier.
   */
  public void addIdentifier(Datatype type, String name, String description) {
    types.add(type);
    identifiers.add(name);
    descriptions.add(description);
  }

  /**
   * Adds a new identifier as the given type and with a description at the specified location.
   *
   * <p>Adds an identifier with the given type and with a description for the Usage message at the
   * specicfied loacation.
   *
   * @param type The type of the identifier.
   * @param name The name of the identifier.
   * @param description The decription of the identifier.
   * @param position The desired position to add the identifier at.
   */
  public void addIdentifier(Datatype type, String name, String description, Integer position) {
    types.add(position, type);
    identifiers.add(position, name);
    descriptions.add(position, description);
  }

  /**
   * Removes the specified identifier and all relevant data, including the value the identifier
   * held.
   *
   * @param identifier The desired identifier to delete.
   */
  public void removeIdentifier(String identifier) {
    int index = getArgumentPosition(identifier);
    types.remove(index);
    identifiers.remove(index);
    arguments.remove(index);
    descriptions.remove(index);
  }

  /**
   * @param identifier The name of the identifier for the desired argument's position.
   * @return an integer that represents the argument's position.
   */
  public int getArgumentPosition(String identifier) {
    int position = identifiers.indexOf(identifier);
    return position;
  }

  public String[] getArgumentsAsStrings() {
    String[] array = new String[arguments.size()];
    int index = 0;
    for (Object value : arguments) {
      array[index] = (String) value;
      index++;
    }
    return array;
  }

  /**
   * Converts the argument list to an array and returns the array.
   *
   * @return an array that holds the arguments passed to the command line.
   */
  public int[] getArgumentsAsIntegers() {
    int[] array = valueInt.stream().mapToInt(i -> i).toArray();
    return array;
  }

  public Float[] getArgumentsAsFloats() {
    Float[] array = new Float[arguments.size()];
    int index = 0;
    for (String value : arguments) {
      array[index] = Float.parseFloat(value);
      index++;
    }
    return array;
  }

  /** @return Returns the Usage message. */
  public String getHelpMessage() {
    helpMessage = constructHelpMessage();
    return helpMessage;
  }

  /** */
  public void setProgramHelpMessage(String message) {
    programHelpMessage = message + "\n\n";
  }

  /** */
  public void setArgumentHelpMessage(int position, String message) {
    descriptions.set(position, message);
  }

  private void convertType(List<String> arguments) {
    int length = arguments.size();
    if (type == Datatype.FLOAT) {
      for (int i = 0; i < length; i++) {
        try {
          valueFloat.add(Float.parseFloat(arguments.get(i)));
        } catch (Exception e) {
          throw new InputMismatchException("The value" + arguments.get(i) + "is not of type float");
        }
      }
    } else if (type == Datatype.INTEGER) {
      for (int i = 0; i < length; i++) {
        try {
          valueInt.add(Integer.parseInt(arguments.get(i)));
        } catch (Exception e) {
          throw new InputMismatchException(
              "The value" + arguments.get(i) + "is not of type integer");
        }
      }
    } else {
      this.arguments = arguments;
    }
  }

  /**
   * Builds the help message based on the current identifiers.
   *
   * @return the Usage message.
   */
  private String constructHelpMessage() {
    helpMessage += programHelpMessage + "Usage:\n";
    for (int i = 0; i < descriptions.size(); i++) {
      Datatype type = types.get(i);
      if (type == Datatype.INTEGER) {
        helpMessage += "  Integer ";
      } else if (type == Datatype.FLOAT) {
        helpMessage += "  Float   ";
      } else {
        helpMessage += "  String  ";
      }
      helpMessage += identifiers.get(i);
      String description = descriptions.get(i);
      if (!description.equals("")) {
        helpMessage += "    - " + description + "\n";
      } else {
        helpMessage += "\n";
      }
    }
    return helpMessage;
  }

  /**
   * Detects if the arguments in command line contains either '--help' or '-h'.
   *
   * @param arguments The command line arguments.
   * @return true if the arguments contain '--help' or '-h', otherwise false.
   */
  private boolean detectHelp(String[] arguments) {
    for (int i = 0; i < arguments.length; i++) {
      if (arguments[i].equals("-h") || arguments[i].equals("--help")) {
        return true;
      }
    }
    return false;
  }
}
