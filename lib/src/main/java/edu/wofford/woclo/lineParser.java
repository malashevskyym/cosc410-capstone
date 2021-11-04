package edu.wofford.woclo;

import java.util.*;

public class LineParser {
  Map<String, Argument> arguments = new HashMap<String, Argument>();
  List<String> argsPosition = new ArrayList<String>();
  String useInfo;

  /** Represents data types. */
  public enum Datatype {
    STRING("STRING"), INTEGER("INT"), FLOAT("FLOAT");

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
  public LineParser() {
  };

  /**
   * Constructs an empty map of named arguments.
   *
   * @param useInfo This variable is for usage information about the program.
   */
  public LineParser(String useInfo) {
    this.useInfo = useInfo;
  }

  /**
   * Specify an argument to come through the command line. Adds the argument to
   * the argument map, declaring a type.
   *
   * @param name The name of the argument (what its called).
   * @param type The data type of the argument (float, int, or string).
   */
  public void addRequiredArgument(String name, Datatype type) {
    arguments.put(name, new Argument(type));
    argsPosition.add(name);
  }

  /**
   * Specify an argument to come through the command line. Adds the argument to
   * the argument map, declaring a type.
   *
   * @param name The name of the argument (what its called).
   * @param type The data type of the argument (float, int, or string).
   * @param help Any additional descriptive help information about the argument.
   */
  public void addRequiredArgument(String name, Datatype type, String help) {
    arguments.put(name, new Argument(type, help));
    argsPosition.add(name);
  }

  /**
   * Request an argument that will be optional in the command line.
   *
   * @param name         The name of the argument (what its called).
   * @param type         The data type of the argument (float, int, or string).
   * @param defaultValue The default value for the optional parameter.
   */
  public void addOptionalArgument(String name, Datatype type, String defaultValue) {
    arguments.put(name, new Argument(type));
    arguments.get(name).setValue(defaultValue);
  }

  /**
   * Request an argument that will be optional in the command line.
   *
   * @param name         The name of the argument (what its called).
   * @param type         The data type of the argument (float, int, or string).
   * @param defaultValue The default value for the optional parameter.
   * @param help         Any additional descriptive help information about the
   *                     argument.
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

  public <T> T getArgument(String identifier) {
    String value = arguments.get(identifier).value;
    return (T) arguments.get(identifier).type.parseType(value);
  }

  /**
   * Parses the given command line arguments and maps them to a value. Returns
   * exceptions under the following cases: -If there are more non-named arguments
   * than identifiers. -If there are less non named arguments than identifiers.
   * -If a given argument value cannot be converted to its specified type. -If a
   * named argument is declared but not followed by a value.
   *
   * @param args String array representation of command line values.
   */
  public void parse(String[] args) {
    List<String> optionals = new ArrayList<String>();
    List<String> required = new ArrayList<String>();
    String helpMessage = constructHelpMessage();
    if (detectHelp(args)) {
      System.out.println(helpMessage);
    } else {
      // Create required list
      for (int i = 0; i < args.length; i++) {
        required.add(args[i]);
      }

      // Create optionals list
      for (int i = 0; i < required.size(); i++) {

        if (required.get(i).substring(0, 1).equals("-")) {

          if (required.get(i).substring(1, 2).equals("-")) {
            if ((i == required.size() - 1) || (required.get(i + 1).substring(0, 1).equals("-")
                && required.get(i + 1).substring(1, 2).equals("-"))) {
              throw new IllegalArgumentException("no value for " + required.get(i));
            }

            optionals.add(args[i]);
            optionals.add(args[i + 1]);
            required.remove(i);
            required.remove(i);
          }
        }
      }
      // Check for too many or too few arguments
      if (required.size() > argsPosition.size()) {
        String excess = required.get(argsPosition.size());
        throw new IllegalArgumentException("the value " + excess + " matches no argument");
      }
      if (required.size() < argsPosition.size()) {
        String excess = argsPosition.get(required.size());
        throw new IllegalArgumentException("the argument " + excess + " is required");
      }
      // Store required values
      for (int i = 0; i < required.size(); i++) {
        if (arguments.get(argsPosition.get(i)).type == Datatype.FLOAT) {
          try {
            Float.parseFloat(required.get(i));
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException("the value " + argsPosition.get(i) + " is not of type float");
          }

        } else if (arguments.get(argsPosition.get(i)).type == Datatype.INTEGER) {

          try {
            Integer.parseInt(required.get(i));
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException("the value " + argsPosition.get(i) + " is not of type integer");
          }
        }
        arguments.get(argsPosition.get(i)).setValue(required.get(i));
      }
      // Store optional values
      for (int i = 0; i < optionals.size(); i++) {
        if (optionals.get(i).substring(0, 1).equals("-")) {
          if (optionals.get(i).substring(1, 2).equals("-")) {
            // This is the fucked up part
            if (arguments.get(optionals.get(i).substring(2)).type == Datatype.FLOAT) {
              try {
                Float.parseFloat(optionals.get(i + 1));
              } catch (NumberFormatException e) {
                throw new IllegalArgumentException("the value " + optionals.get(i) + " is not of type float");
              }

            } else if (arguments.get(optionals.get(i).substring(2)).type == Datatype.INTEGER) {

              try {
                Integer.parseInt(optionals.get(i + 1));
              } catch (NumberFormatException e) {
                throw new IllegalArgumentException("the value " + optionals.get(i) + " is not of type integer");
              }
            }
            // Fuckiness end here
            arguments.get(optionals.get(i).substring(2)).setValue(optionals.get(i + 1));
          }
        }
      }
    }
  }

  /**
   * Constructs a help usage message based on argument description values.
   *
   * @return A string containing usage information.
   */
  private String constructHelpMessage() {
    String helpMessage = "";

    helpMessage += "Usage:";
    helpMessage += useInfo + "\npositional arguments:";
    StringBuffer buf = new StringBuffer();
    for (Map.Entry<String, Argument> entry : arguments.entrySet()) {

      if (entry.getValue().type == Datatype.INTEGER) {
        buf.append(entry.getKey() + "          " + "(integer)" + "     " + entry.getValue().help);
      } else if (entry.getValue().type == Datatype.FLOAT) {
        buf.append(entry.getKey() + "          " + "(float)" + "     " + entry.getValue().help);
      } else if (entry.getValue().type == Datatype.STRING) {
        buf.append(entry.getKey() + "          " + "(string)" + "     " + entry.getValue().help);
      }
    }

    helpMessage += "named arguments:\n -h, --help  show this help message and exit";
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
