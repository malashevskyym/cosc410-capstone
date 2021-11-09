package edu.wofford.woclo;

import java.util.*;

public class LineParser {
  Map<String, Argument> arguments = new HashMap<String, Argument>();
  List<String> argumentNameByPosition = new ArrayList<String>();
  private List<String> required = new ArrayList<String>();
  private List<String> optional = new ArrayList<String>();
  String useInfo;

  /** Represents data types. */
  public enum Datatype {
    STRING("STRING"),
    INTEGER("INT"),
    FLOAT("FLOAT");

    public final String label;

    private Datatype(String label) {
      this.label = label;
    }

    @SuppressWarnings("unchecked")
    public <T> T parseType(String value) {
      switch (this) {
        case INTEGER:
          return (T) Integer.valueOf(value);
        case FLOAT:
          return (T) Float.valueOf(value);
        default:
          return (T) value;
      }
    }
  };

  /** Constructs an empty map of named arguments. */
  public LineParser() {};

  /**
   * Constructs an empty map of named arguments.
   *
   * @param useInfo This variable is for usage information about the program.
   */
  public LineParser(String useInfo) {
    this.useInfo = useInfo;
  }

  /**
   * Specify an argument to come through the command line. Adds the argument to the argument map,
   * declaring a type.
   *
   * @param name The name of the argument (what its called).
   * @param type The data type of the argument (float, int, or string).
   */
  public void addRequiredArgument(String name, Datatype type) {
    arguments.put(name, new Argument(type));
    argumentNameByPosition.add(name);
  }

  /**
   * Specify an argument to come through the command line. Adds the argument to the argument map,
   * declaring a type.
   *
   * @param name The name of the argument (what its called).
   * @param type The data type of the argument (float, int, or string).
   * @param help Any additional descriptive help information about the argument.
   */
  public void addRequiredArgument(String name, Datatype type, String help) {
    arguments.put(name, new Argument(type, help));
    argumentNameByPosition.add(name);
  }

  /**
   * Request an argument that will be optional in the command line.
   *
   * @param name The name of the argument (what its called).
   * @param type The data type of the argument (float, int, or string).
   * @param defaultValue The default value for the optional parameter.
   */
  public void addOptionalArgument(String name, Datatype type, String defaultValue) {
    arguments.put(name, new Argument(type));
    arguments.get(name).setValue(defaultValue);
  }

  /**
   * Request an argument that will be optional in the command line.
   *
   * @param name The name of the argument (what its called).
   * @param type The data type of the argument (float, int, or string).
   * @param defaultValue The default value for the optional parameter.
   * @param help Any additional descriptive help information about the argument.
   */
  public void addOptionalArgument(String name, Datatype type, String defaultValue, String help) {
    arguments.put(name, new Argument(type, help));
    arguments.get(name).setValue(defaultValue);
  }

  /**
   * Displays a representation of the map at current.
   *
   * @return An array representation of elements in the map.
   */
  public String[][] getArg() {
    String[][] arg = new String[arguments.keySet().size()][4];
    int i = 0;
    for (Map.Entry<String, Argument> entry : arguments.entrySet()) {
      arg[i][0] = entry.getKey();
      arg[i][1] = arguments.get(entry.getKey()).type.label;
      arg[i][2] = arguments.get(entry.getKey()).value;
      arg[i][3] = arguments.get(entry.getKey()).help;
      i++;
    }
    return arg;
  }

  /**
   * Parses the desired argument to its type and returns the value as that type.
   *
   * @param identifer The key for the desired argument.
   * @return The argument parsed to its type.
   */
  @SuppressWarnings("unchecked")
  public <T> T getArgument(String identifier) {
    String value = arguments.get(identifier).value;
    return (T) arguments.get(identifier).type.parseType(value);
  }

  /**
   * Checks that the arguments passed into the command line match with the arguments that have been
   * added.
   *
   * @param args The command line arguments.
   */
  private void checkArgumentsForErrors(String[] args) {
    // TODO: Make sure that second conditional works correctly, or if it is my code in Equivalent
    // Strings that needs changing.

    // Check that the correct amount of required arguments were passed.
    if (required.size() > argumentNameByPosition.size()) {
      String excess = required.get(argumentNameByPosition.size());
      throw new IllegalArgumentException("the value " + excess + " matches no argument");
    }
    if (required.size() < argumentNameByPosition.size()) {
      String excess = argumentNameByPosition.get(required.size());
      throw new IllegalArgumentException("the argument " + excess + " is required");
    }

    // Check that optional arguments have a value.
    for (int i = 0; i < args.length; i++) {
      if (args[i].substring(0, 1).equals("-")) {
        if (i == args.length - 1 || args[i + 1].substring(0, 1).equals("-")) {
          throw new IllegalArgumentException("no value for " + args[i]);
        }
      }
    }

    // TODO: Make sure that user does not pass in named/optional arguments that DO NOT exist.
  }

  /**
   * Builds the required list and optional list.
   *
   * @param args The arguments passed in at the command line.
   */
  private void buildArgumentLists(String[] args) {

    for (int i = 0; i < args.length; i++) {
      if (args[i].substring(0, 1).equals("-")) {
        optional.add(args[i]);
        optional.add(args[i + 1]);
        i++;
      } else {
        required.add(args[i]);
      }
    }

    checkArgumentsForErrors(args);
  }

  /**
   * Checks that the arguments passed into the command line can be parsed into their specified
   * types.
   */
  private void checkArgumentsForTypeEquivalence() {
    for (Map.Entry<String, Argument> entry : arguments.entrySet()) {
      String value = entry.getValue().getValue();
      Datatype type = entry.getValue().type;
      if (type == Datatype.FLOAT) {
        try {
          Float.parseFloat(value);
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("the value " + value + " is not of type float");
        }
      } else if (type == Datatype.INTEGER) {
        try {
          Integer.parseInt(value);
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("the value " + value + " is not of type integer");
        }
      }
    }
  }

  /**
   * Parses the given command line arguments and maps them to a value. Returns exceptions under the
   * following cases: -If there are more non-named arguments than identifiers. -If there are less
   * non named arguments than identifiers. -If a given argument value cannot be converted to its
   * specified type. -If a named argument is declared but not followed by a value.
   *
   * @param args String array representation of command line values.
   */
  public void parse(String[] args) {
    String helpMessage = constructHelpMessage();
    if (detectHelp(args)) {
      System.out.println(helpMessage);
    } else {
      buildArgumentLists(args);

      for (int i = 0; i < required.size(); i++) {
        arguments.get(argumentNameByPosition.get(i)).setValue(required.get(i));
      }

      for (int i = 0; i < optional.size(); i++) {
        if (optional.get(i).substring(0, 1).equals("-")) {
          arguments.get(optional.get(i).substring(2)).setValue(optional.get(i + 1));
        }
      }
      checkArgumentsForTypeEquivalence();
    }
  }

  /**
   * Constructs a help usage message based on argument description values.
   *
   * @return A string containing usage information.
   */
  private String constructHelpMessage() {
    String helpMessage = "";

    helpMessage += "usage: " + useInfo + "\n\npositional arguments:";
    StringBuffer buf = new StringBuffer();
    for (Map.Entry<String, Argument> entry : arguments.entrySet()) {

      if (entry.getValue().type == Datatype.INTEGER) {
        buf.append(
            "\n " + entry.getKey() + "     " + "(integer)" + "      " + entry.getValue().help);
      } else if (entry.getValue().type == Datatype.FLOAT) {
        buf.append("\n " + entry.getKey() + "     " + "(float)" + "      " + entry.getValue().help);
      } else if (entry.getValue().type == Datatype.STRING) {
        buf.append(
            "\n " + entry.getKey() + "     " + "(string)" + "      " + entry.getValue().help);
      }
    }

    helpMessage += buf;
    helpMessage += "\n\nnamed arguments:\n -h, --help  show this help message and exit";
    return helpMessage;
  }

  /**
   * Detects a help command in the command line.
   *
   * @param arguments Command line arguments in string form.
   * @return Boolean as to whether or not --help or -h is detected.
   */
  public boolean detectHelp(String[] arguments) {
    for (int i = 0; i < arguments.length; i++) {
      if (arguments[i].equals("-h") || arguments[i].equals("--help")) {
        return true;
      }
    }
    return false;
  }
}
