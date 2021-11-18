package edu.wofford.woclo;

import java.util.*;

public class LineParser {
  private Map<String, Argument> arguments = new HashMap<String, Argument>();
  private List<String> argumentNameByPosition = new ArrayList<String>();
  private String usageInfo;
  private String programInfo;

  /** Represents data types. */
  public enum Datatype {
    STRING("STRING"),
    INTEGER("INT"),
    FLOAT("FLOAT"),
    BOOLEAN("BOOLEAN");

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
        case BOOLEAN:
          return (T) Boolean.valueOf(value);
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
   * @param usageInfo This is for usage information about the program.
   */
  public LineParser(String usageInfo) {
    this(usageInfo, "");
  }

  /**
   * Constructs an empty map of named arguments.
   *
   * @param usageInfo This is for usage information about the program.
   * @param programInfo This is for the program's description.
   */
  public LineParser(String usageInfo, String programInfo) {
    this.usageInfo = usageInfo;
    this.programInfo = programInfo;
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
   * Specify an argument to come through the command line. Adds the argument to the argument map,
   * declaring a type.
   *
   * @param name The name of the argument (what its called).
   * @param type The data type of the argument (float, int, or string).
   * @param help Any additional descriptive help information about the argument.
   */
  public void addRequiredArgument(
      String name, Datatype type, String help, String[] discreteValues) {
    arguments.put(name, new Argument(type, help));
    argumentNameByPosition.add(name);
    checkDiscreteValueTypes(arguments.get(name).type, discreteValues);
    arguments.get(name).discreteValues = discreteValues;
  }

  /**
   * Request an argument that will be optional in the command line. Since no default has been added
   * values will be set to default.
   *
   * @param name The name of the argument (what its called).
   * @param type The data type of the argument (float, int, or string).
   */
  public void addOptionalArgument(String name, Datatype type) {
    arguments.put(name, new Argument(type));
    if (type == Datatype.BOOLEAN) {
      arguments.get(name).setValue("false");
    } else if (type == Datatype.INTEGER) {
      arguments.get(name).setValue("0");
    } else if (type == Datatype.FLOAT) {
      arguments.get(name).setValue("0.0");
    } else {
      arguments.get(name).setValue("");
    }
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

  public void addOptionalArgument(
      String name, Datatype type, String defaultValue, String help, String shortName) {
    arguments.put(name, new Argument(type, help));
    arguments.get(name).setValue(defaultValue);
    arguments.get(name).shortName = shortName;
  }

  public void addOptionalArgument(
      String name,
      Datatype type,
      String defaultValue,
      String help,
      String shortName,
      String[] discreteValues) {
    arguments.put(name, new Argument(type, help));
    arguments.get(name).setValue(defaultValue);
    arguments.get(name).shortName = shortName;
    checkDiscreteValueTypes(arguments.get(name).type, discreteValues);
    arguments.get(name).discreteValues = discreteValues;
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
   * @param identifier identifer The key for the desired argument.
   * @return The argument parsed to its type.
   */
  @SuppressWarnings("unchecked")
  public <T> T getArgument(String identifier) {
    String value = arguments.get(identifier).value;
    return (T) arguments.get(identifier).type.parseType(value);
  }

  // TODO: Make sure that user does not pass in named/optional arguments that DO
  // NOT exist.

  /**
   * Checks that the arguments passed into the command line can be parsed into their specified
   * types.
   */
  private void checkArgumentsForTypeEquivalence(Datatype type, String value) {
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

  /**
   * Checks if given value has a discrete list, if it does, verify that value is within discreet
   * list.
   *
   * @param argumentName Name of argument in map.
   * @param values Value of argument on command line.
   */
  private void checkValueIsDiscrete(String argumentName, String values) {
    if (arguments.get(argumentName).discreteValues.length > 0) {

      if (!Arrays.asList(arguments.get(argumentName).discreteValues).contains(values)) {
        throw new IllegalArgumentException(
            argumentName
                + " value "
                + values
                + " is not a member of "
                + Arrays.toString(arguments.get(argumentName).discreteValues));
      }
    }
  }

  /**
   * Checks discrete values are of proper type.
   *
   * @param type Type of data of argument.
   * @param values Possible values for the argument.
   */
  private void checkDiscreteValueTypes(Datatype type, String[] values) {
    for (String value : values) {
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
   * Get the long form name of a short form of a named optional argument.
   *
   * @param shortName The short form name.
   * @return The long form name in the map.
   */
  private String getLongForm(String shortName) {
    String s = "";
    for (Map.Entry<String, Argument> entry : arguments.entrySet()) {
      if (entry.getValue().shortName.equals(shortName)) {
        s = entry.getKey();
        break;
      }
    }

    return s;
  }

  private boolean checkCombinedShortForms(String shortForm) {

    String[] shortnames = shortForm.split("");
    for (String letters : shortnames) {

      for (Map.Entry<String, Argument> entry : arguments.entrySet()) {
        if (entry.getValue().shortName.equals(letters)) {

          return true;
        }
      }
    }
    return false;
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
    if (detectHelp(args)) {
      System.out.println(constructHelpMessage());
    } else {
      Queue<String> q = new LinkedList<>(Arrays.asList(args));

      int position = 0;
      // While this queue has things in it.
      while (q.isEmpty() == false) {
        String current = q.remove();

        if (current.startsWith("-")
            && (arguments.containsKey(current.substring(2))
                || !getLongForm(current.substring(1)).equals("")
                || checkCombinedShortForms(current.substring(1)))) {
          // If it is named long form
          if (current.substring(1, 2).equals("-")) {

            String value = q.peek();
            Datatype type = arguments.get(current.substring(2)).type;
            // If it is a boolean argument
            if (arguments.get(current.substring(2)).type == Datatype.BOOLEAN) {
              arguments.get(current.substring(2)).value = "true";
            } else {
              // Remove from queue, get type.

              if (q.isEmpty()) {
                throw new IllegalArgumentException("no value for " + current.substring(2));
              }
              checkArgumentsForTypeEquivalence(type, value);
              if ((q.peek().startsWith("--"))) {
                throw new IllegalArgumentException("no value for " + current.substring(2));
              }
              checkValueIsDiscrete(current.substring(2), value);
              arguments.get(current.substring(2)).setValue(value);
              q.remove();
            }
            // If optional is named shortform
          } else {

            String value = q.peek();
            String longName = getLongForm(current.substring(1));

            if (current.substring(1).length() > 1
                || arguments.get(longName).type == Datatype.BOOLEAN) {
              String[] flags = current.substring(1).split("");

              for (String letter : flags) {
                String individualLetter = getLongForm(letter);
                arguments.get(individualLetter).value = "true";
              }

            } else {
              // Remove from queue, get type.
              Datatype type = arguments.get(longName).type;
              if (q.isEmpty()) {
                throw new IllegalArgumentException("no value for " + longName);
              }
              checkArgumentsForTypeEquivalence(type, value);
              if (((q.peek().startsWith("--") || q.peek().startsWith("-")))
                  && checkCombinedShortForms(q.peek())) {
                throw new IllegalArgumentException("no value for " + longName);
              }
              checkValueIsDiscrete(longName, value);
              arguments.get(longName).setValue(value);
              q.remove();
            }
          }
          // If it is required
        } else {
          if (position > argumentNameByPosition.size() - 1) {
            String excess = current;
            throw new IllegalArgumentException("the value " + excess + " matches no argument");
          }
          Datatype type = arguments.get(argumentNameByPosition.get(position)).type;
          String value = current;
          checkArgumentsForTypeEquivalence(type, value);
          checkValueIsDiscrete(argumentNameByPosition.get(position), value);
          arguments.get(argumentNameByPosition.get(position)).setValue(current);
          position++;
        }
      }
      if (argumentNameByPosition.size() - 1 >= position) {
        throw new IllegalArgumentException(
            "the argument " + argumentNameByPosition.get(position) + " is required");
      }
    }
  }

  /**
   * Constructs a help usage message based on argument description values.
   *
   * @return A string containing usage information.
   */
  // If you enjoy your sanity don't touch this function.
  private String constructHelpMessage() {
    String helpMessage = "usage: " + usageInfo + "\n\n" + programInfo + "\n\n";

    StringBuffer buffer = new StringBuffer();
    helpMessage += "positional arguments:\n";
    int largestWord = "-h, --help".length();
    for (Map.Entry<String, Argument> entry : arguments.entrySet()) {
      if (argumentNameByPosition.contains(entry.getKey()) == false) {
        if (entry.getValue().type == Datatype.BOOLEAN) {
          String variable = "--" + entry.getKey();
          if (variable.length() > largestWord) {
            largestWord = variable.length();
          }
        } else {
          String variable = "--" + entry.getKey() + " " + entry.getKey();
          if (variable.length() > largestWord) {
            largestWord = variable.length();
          }
        }
      } else {
        String variable = entry.getKey();
        if (variable.length() > largestWord) {
          largestWord = variable.length();
        }
      }
    }
    int spaces = 0;
    for (int i = 0; i < argumentNameByPosition.size(); i++) {
      spaces = largestWord + 2;
      // 18 spaces + 14 spaces + argument help message
      spaces = spaces - argumentNameByPosition.get(i).length();
      buffer.append(" " + argumentNameByPosition.get(i));
      for (int j = 0; j < spaces; j++) {
        buffer.append(" ");
      }

      spaces = 14;
      String type = "";
      if (arguments.get(argumentNameByPosition.get(i)).type == Datatype.STRING) {
        type = "(string)";
      } else if (arguments.get(argumentNameByPosition.get(i)).type == Datatype.INTEGER) {
        type = "(integer)";
      } else if (arguments.get(argumentNameByPosition.get(i)).type == Datatype.FLOAT) {
        type = "(float)";
      }
      buffer.append(type);
      spaces = spaces - type.length();
      for (int j = 0; j < spaces; j++) {
        buffer.append(" ");
      }

      buffer.append(arguments.get(argumentNameByPosition.get(i)).help + "\n");
    }
    helpMessage += buffer;
    buffer.delete(0, buffer.length());
    helpMessage += "\nnamed arguments:\n -h, --help";
    spaces = largestWord + 2;
    spaces = spaces - "-h, --help".length();
    for (int j = 0; j < spaces; j++) {
      buffer.append(" ");
    }
    buffer.append("show this help message and exit");

    for (Map.Entry<String, Argument> entry : arguments.entrySet()) {
      // 18 spaces + 14 spaces + argument help message + (default: value) (except for
      // -h/--help)
      if (argumentNameByPosition.contains(entry.getKey()) == false) {
        spaces = largestWord + 2;
        if (entry.getValue().type == Datatype.BOOLEAN) {
          String variable = "--" + entry.getKey();
          buffer.append("\n ");
          buffer.append(variable);
          spaces = spaces - variable.length();
          for (int j = 0; j < spaces; j++) {
            buffer.append(" ");
          }
          buffer.append(arguments.get(entry.getKey()).value);
        } else {
          String variable =
              "--" + entry.getKey() + " " + entry.getKey().toUpperCase(Locale.getDefault());
          buffer.append("\n ");
          buffer.append(variable);
          spaces = spaces - variable.length();
          for (int j = 0; j < spaces; j++) {
            buffer.append(" ");
          }
          spaces = 14;
          String type = "";
          if (arguments.get(entry.getKey()).type == Datatype.STRING) {
            type = "(string)";
          } else if (arguments.get(entry.getKey()).type == Datatype.INTEGER) {
            type = "(integer)";
          } else if (arguments.get(entry.getKey()).type == Datatype.FLOAT) {
            type = "(float)";
          }
          buffer.append(type);
          spaces = spaces - type.length();
          for (int j = 0; j < spaces; j++) {
            buffer.append(" ");
          }

          buffer.append(arguments.get(entry.getKey()).help + " ");
          buffer.append("(default: " + arguments.get(entry.getKey()).value + ")");
        }
      }
    }
    helpMessage += buffer;

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
