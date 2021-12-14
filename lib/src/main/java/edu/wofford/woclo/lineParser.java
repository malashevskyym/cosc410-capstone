package edu.wofford.woclo;

import java.io.*;
import java.io.File;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * This program parses command line arguemnts. A required argument must appear in the command line
 * in the position it was given. For example, if the user has created a new instance of lineparser
 * called parse: If the user passes in parser.addRequiredArgument(height, Datatype.INTEGER);
 * parser.addRequiredArgument(wight, Datatype.INTEGER); After the using calls
 * parser.parse(commandline), A command line with the values ["1","2"] will set the value of height
 * to 1 and width to 2. These values can be retreived through the method getArgument.
 * parser.getArgument("height") will return 1. A command line with the values ["1"] will return an
 * error. An optional value is a value that may or may not appear on the command line. Optional
 * arguments can be added using addOptionalArgument() method and can be used as flags. The flag -h
 * and --help will both halt parsing and print a help message.
 */
public class LineParser {
  private Map<String, Argument> arguments = new HashMap<String, Argument>();
  private List<String> argumentNameByPosition = new ArrayList<String>();
  private List<String> optionalArgument = new ArrayList<String>();
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
   * Constructs an empty map of named arguments. Usage information describes program information,
   * such as prupose and expected values. Usage information will appear at the top of a help
   * message. For example: usage: java EquivalentStrings [-h] string1 string2
   *
   * <p>positional arguments: string1 (string) the first string string2 (string) the second string
   *
   * <p>named arguments: -h, --help show this help message and exit
   *
   * @param usageInfo This is for usage information about the program.
   */
  public LineParser(String usageInfo) {
    this(usageInfo, "");
  }

  /**
   * Constructs an empty map of named arguments. Program information will appear in help message,
   * describing the purpose of the program. usage: java EquivalentStrings [-h] string1 string2
   *
   * <p>Determine if two strings are equivalent.
   *
   * <p>positional arguments: string1 (string) the first string string2 (string) the second string
   *
   * <p>named arguments: -h, --help show this help message and exit
   *
   * @param usageInfo This is for usage information about the program.
   * @param programInfo This is for the program's description.
   */
  public LineParser(String usageInfo, String programInfo) {
    this.usageInfo = usageInfo;
    this.programInfo = programInfo;
  }

  /**
   * Constructs an argument map based on an xml file, tag represent argument type, name, and
   * restricted input. <?xml version="1.0"?> <arguments> <positionalArgs> <positional>
   * <type>float</type> <description>the length of the volume</description> <name>length</name>
   * </positional> <positional> <type>float</type> <name>width</name> <description>the width of the
   * volume</description> </positional> <positional> <description>the height of the
   * volume</description> <name>height</name> <type>float</type> </positional> </positionalArgs>
   * <namedArgs> <named> <description>the type of volume</description> <shortname>t</shortname>
   * <type>string</type> <name>type</name> <restrictions> <restriction>box</restriction>
   * <restriction>pyramid</restriction> <restriction>ellipsoid</restriction> </restrictions>
   * <default> <value>box</value> </default> </named> <named> <default> <value>4</value> </default>
   * <type>integer</type> <description>the maximum number of decimal places for the
   * volume</description> <name>precision</name> <shortname>p</shortname> </named> </namedArgs>
   * </arguments>
   *
   * @param filename
   */
  public void addArgsFromString(String Filename) {
    try {
      // Building instance of root node
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(new InputSource(new StringReader(Filename)));
      Element root = document.getDocumentElement();

      NodeList positionals = root.getElementsByTagName("positional");
      NodeList optionals = root.getElementsByTagName("named");
      // Getting elements for required
      for (int i = 0; i < positionals.getLength(); i++) {
        Node node = positionals.item(i);
        Element element = (Element) node;

        String strType = element.getElementsByTagName("type").item(0).getTextContent();
        String description = "";
        try {
          description = element.getElementsByTagName("description").item(0).getTextContent();
        } catch (NullPointerException e) {

        }
        String name = element.getElementsByTagName("name").item(0).getTextContent();
        Datatype type = Datatype.STRING;
        if (strType.equals("float")) {
          type = Datatype.FLOAT;
        } else if (strType.equals("integer")) {
          type = Datatype.INTEGER;
        } else if (strType.equals("string")) {
          type = Datatype.STRING;
        } else if (strType.equals("boolean")) {
          type = Datatype.BOOLEAN;
        }
        addRequiredArgument(name, type, description);
      }
      // Getting elements for optionals
      for (int i = 0; i < optionals.getLength(); i++) {
        Node node = optionals.item(i);
        Element element = (Element) node;

        String strType = element.getElementsByTagName("type").item(0).getTextContent();
        String description = element.getElementsByTagName("description").item(0).getTextContent();
        String name = element.getElementsByTagName("name").item(0).getTextContent();

        String shortName = "";
        try {
          shortName = element.getElementsByTagName("shortname").item(0).getTextContent();
        } catch (NullPointerException e) {

        }

        String defaultVal = element.getElementsByTagName("default").item(0).getTextContent().trim();
        String[] restrictionsArr = new String[0];

        NodeList restrictionsNode = element.getElementsByTagName("restriction");
        List<String> restrictions = new ArrayList<String>();

        if (restrictionsNode.getLength() > 0) {
          for (int j = 0; j < restrictionsNode.getLength(); j++) {
            String individualRestriction = restrictionsNode.item(j).getTextContent();
            restrictions.add(individualRestriction);
          }
          restrictionsArr = new String[restrictions.size()];
          restrictionsArr = restrictions.toArray(restrictionsArr);
        }

        Datatype type = Datatype.STRING;
        if (strType.equals("float")) {
          type = Datatype.FLOAT;
        } else if (strType.equals("integer")) {
          type = Datatype.INTEGER;
        } else if (strType.equals("string")) {
          type = Datatype.STRING;
        } else if (strType.equals("boolean")) {
          type = Datatype.BOOLEAN;
        }
        if (restrictionsArr.length > 0) {
          addOptionalArgument(name, type, defaultVal, description, shortName, restrictionsArr);
        } else {
          addOptionalArgument(name, type, defaultVal, description, shortName);
        }
      }
    } catch (SAXException e) {
      throw new InvalidXMLException();
    } catch (NullPointerException e) {
      throw new InvalidXMLException();
    } catch (ParserConfigurationException e) {
      throw new InvalidXMLException();
    } catch (IOException e) {
      throw new InvalidXMLException();
    }
  }

  /**
   * This program constructs an xml file of the given filename using the map in this instance of
   * LineParser. The xml file will be constructed in a similar format to the xml file given above.
   *
   * @param filename Name of xml file to be constructed.
   */
  public void createXmlFile(String filename) {
    new File(filename);
    Writer myWriter = null;
    try {
      myWriter = new OutputStreamWriter(new FileOutputStream(filename), "UTF-8");
      myWriter.write("<?xml version=\"1.0\"?>\n");
      myWriter.write("<arguments>\n");

      myWriter.write("<positionalArgs>\n");
      for (int i = 0; i < argumentNameByPosition.size(); i++) {
        myWriter.write("<positional>\n");
        myWriter.write("<name>" + argumentNameByPosition.get(i) + "</name>\n");

        myWriter.write(getEntry(argumentNameByPosition.get(i)).toString());

        myWriter.write("</positional>\n");
      }
      myWriter.write("</positionalArgs>\n");

      myWriter.write("<namedArgs>\n");
      for (int i = 0; i < optionalArgument.size(); i++) {
        myWriter.write("<named>\n");
        myWriter.write("<name>" + optionalArgument.get(i) + "</name>\n");
        myWriter.write(getEntry(optionalArgument.get(i)).toString());

        myWriter.write("</named>\n");
      }
      myWriter.write("</namedArgs>\n");

      myWriter.write("</arguments>");
      myWriter.close();

    } catch (IOException e) {

      System.out.println("An error occurred while writing to file");

      e.printStackTrace();
    } finally {
      try {
        if (myWriter != null) {
          myWriter.close();
        }
      } catch (IOException e) {

      }
    }
  }

  /**
   * A private method for getting an argument object with the given key. Cannot be called before
   * parse() or getArgsFromFile() is called.
   *
   * @param key The name of the argument.
   * @return An argument object.
   */
  private Argument getEntry(String key) {
    Argument arg = new Argument();
    for (Map.Entry<String, Argument> entry : arguments.entrySet()) {
      if (entry.getKey().equals(key)) {
        arg = entry.getValue();
        break;
      }
    }
    return arg;
  }

  /**
   * Specify an argument to come through the command line. Adds the argument to the argument map,
   * declaring a type. A required argument must appear in the command line in the position it was
   * given. For example, if the user has created a new instance of lineparser called parse: If the
   * user passes in parser.addRequiredArgument(height, Datatype.INTEGER);
   * parser.addRequiredArgument(wight, Datatype.INTEGER); After the using calls
   * parser.parse(commandline), A command line with the values ["1","2"] will set the value of
   * height to 1 and width to 2. These values can be retreived through the method getArgument.
   * parser.getArgument("height") will return 1. A command line with the values ["1"] will return an
   * error.
   *
   * @param name The name of the argument (what its called, a moniker for an entry in the command
   *     line).
   * @param type The data type of the argument (float, int, string, or boolean).
   */
  public void addRequiredArgument(String name, Datatype type) {
    arguments.put(name, new Argument(type));
    argumentNameByPosition.add(name);
  }

  /**
   * Specify an argument to come through the command line. Adds the argument to the argument map,
   * declaring a type. /** Specify an argument to come through the command line. Adds the argument
   * to the argument map, declaring a type. A required argument must appear in the command line in
   * the position it was given. For example, if the user has created a new instance of lineparser
   * called parse: If the user passes in parser.addRequiredArgument(height, Datatype.INTEGER);
   * parser.addRequiredArgument(wight, Datatype.INTEGER); After the using calls
   * parser.parse(commandline), A command line with the values ["1","2"] will set the value of
   * height to 1 and width to 2. These values can be retreived through the method getArgument.
   * parser.getArgument("height") will return 1. A command line with the values ["1"] will return an
   * error.
   *
   * <p>This constructor adds additional help information to the argument, so that the argument is
   * displayed in the help message.
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
   * declaring a type. A required argument must appear in the command line in the position it was
   * given. For example, if the user has created a new instance of lineparser called parse: If the
   * user passes in parser.addRequiredArgument(height, Datatype.INTEGER);
   * parser.addRequiredArgument(weight, Datatype.INTEGER); After the using calls
   * parser.parse(commandline), A command line with the values ["1","2"] will set the value of
   * height to 1 and width to 2. These values can be retreived through the method getArgument.
   * parser.getArgument("height") will return 1. A command line with the values ["1"] will return an
   * error.
   *
   * <p>This constructor allows additional discreete values to be included. If a value is not a
   * member of discreete values, an error is thrown. A specification of
   * parser.addRequiredArgument("height", Datatype.INTEGER, "", new String[]{"1","2"}) will throw an
   * error if height is not parsed to either 1 or 2.
   *
   * @param name The name of the argument (what its called).
   * @param type The data type of the argument (float, int, or string).
   * @param help Any additional descriptive help information about the argument.
   * @param discreteValues String array of allowed values.
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
   * values will be set to default. Optional arguments may or may not appear on the command line, an
   * error will not be thrown. There are a few types of optional arguments: long form "-type",
   * shortform "-t", and combined shortform "-tsm". Combined short forms act as flag, they are set
   * as booleans and if they appear in the command line are set to value "true". Boolean do not take
   * a secondary command line value. Their appearance on the command line sets them to true.
   * Otherwise the next positional argument on the command line is taken as a value.
   *
   * <p>To add an argument with this contructor, an example would be:
   * parser.addOptionalArgument("shape", Datatype.String);
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
    optionalArgument.add(name);
  }

  /**
   * Request an argument that will be optional in the command line. Since no default has been added
   * values will be set to default. Optional arguments may or may not appear on the command line, an
   * error will not be thrown. There are a few types of optional arguments: long form "-type",
   * shortform "-t", and combined shortform "-tsm". Combined short forms act as flag, they are set
   * as booleans and if they appear in the command line are set to value "true". Boolean do not take
   * a secondary command line value. Their appearance on the command line sets them to true.
   * Otherwise the next positional argument on the command line is taken as a value. A default value
   * will set a default value on the optional argument in case it is not passed in.
   *
   * <p>To add an argument with this contructor, an example would be:
   * parser.addOptionalArgument("shape", Datatype.String, "rectangle");
   *
   * @param name The name of the argument (what its called).
   * @param type The data type of the argument (float, int, or string).
   * @param defaultValue The default value for the optional parameter.
   */
  public void addOptionalArgument(String name, Datatype type, String defaultValue) {
    arguments.put(name, new Argument(type));
    arguments.get(name).setValue(defaultValue);
    optionalArgument.add(name);
  }

  /**
   * Request an argument that will be optional in the command line. Since no default has been added
   * values will be set to default. Optional arguments may or may not appear on the command line, an
   * error will not be thrown. There are a few types of optional arguments: long form "-type",
   * shortform "-t", and combined shortform "-tsm". Combined short forms act as flag, they are set
   * as booleans and if they appear in the command line are set to value "true". Boolean do not take
   * a secondary command line value. Their appearance on the command line sets them to true.
   * Otherwise the next positional argument on the command line is taken as a value. A default value
   * will set a default value on the optional argument in case it is not passed in. A help value
   * will specify help information for an optional value so it appears on the help/usage message.
   *
   * <p>To add an argument with this contructor, an example would be:
   * parser.addOptionalArgument("shape", Datatype.String, "rectangle", "The desired shape of the
   * polygon.");
   *
   * @param name The name of the argument (what its called).
   * @param type The data type of the argument (float, int, or string).
   * @param defaultValue The default value for the optional parameter.
   * @param help Any additional descriptive help information about the argument.
   */
  public void addOptionalArgument(String name, Datatype type, String defaultValue, String help) {
    arguments.put(name, new Argument(type, help));
    arguments.get(name).setValue(defaultValue);
    optionalArgument.add(name);
  }

  /**
   * Request an argument that will be optional in the command line. Since no default has been added
   * values will be set to default. Optional arguments may or may not appear on the command line, an
   * error will not be thrown. There are a few types of optional arguments: long form "-type",
   * shortform "-t", and combined shortform "-tsm". Combined short forms act as flag, they are set
   * as booleans and if they appear in the command line are set to value "true". Boolean do not take
   * a secondary command line value. Their appearance on the command line sets them to true.
   * Otherwise the next positional argument on the command line is taken as a value. A default value
   * will set a default value on the optional argument in case it is not passed in. A help value
   * will specify help information for an optional value so it appears on the help/usage message.
   *
   * <p>To add an argument with this contructor, an example would be:
   * parser.addOptionalArgument("triangle", Datatype.BOOLEAN, "false", "The desired shape of the
   * polygon.", "-t");
   *
   * @param name The name of the argument (what its called).
   * @param type The data type of the argument (float, int, or string).
   * @param defaultValue The default value for the optional parameter.
   * @param help Any additional descriptive help information about the argument.
   * @param shortName Shortform description of argument.
   */
  public void addOptionalArgument(
      String name, Datatype type, String defaultValue, String help, String shortName) {
    arguments.put(name, new Argument(type, help));
    arguments.get(name).setValue(defaultValue);
    arguments.get(name).shortName = shortName;
    optionalArgument.add(name);
  }

  /**
   * Request an argument that will be optional in the command line. Since no default has been added
   * values will be set to default. Optional arguments may or may not appear on the command line, an
   * error will not be thrown. There are a few types of optional arguments: long form "-type",
   * shortform "-t", and combined shortform "-tsm". Combined short forms act as flag, they are set
   * as booleans and if they appear in the command line are set to value "true". Boolean do not take
   * a secondary command line value. Their appearance on the command line sets them to true.
   * Otherwise the next positional argument on the command line is taken as a value. A default value
   * will set a default value on the optional argument in case it is not passed in. A help value
   * will specify help information for an optional value so it appears on the help/usage message.
   * Discrete values can also be specified for optiional arguments.
   *
   * <p>To add an argument with this contructor, an example would be:
   * parser.addOptionalArgument("shape", Datatype.String, "rectangle", "The desired shape of the
   * polygon.","-s", new String[]{"rectangle", "pyramid", "triangle"});
   *
   * @param name The name of the argument (what its called).
   * @param type The data type of the argument (float, int, or string).
   * @param defaultValue The default value for the optional parameter.
   * @param help Any additional descriptive help information about the argument.
   * @param shortName Shortform description of argument.
   * @param discreteValues Restricted values of the argument.
   */
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
    optionalArgument.add(name);
  }

  /**
   * Displays a representation of the map at current in nested array format.
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
   * Parses the desired argument to its type and returns the value as that type. This method can
   * only be called after the parse() method.
   *
   * @param identifier identifer The key for the desired argument.
   * @return The argument parsed to its type.
   */
  @SuppressWarnings("unchecked")
  public <T> T getArgument(String identifier) {
    String value = arguments.get(identifier).value;
    return (T) arguments.get(identifier).type.parseType(value);
  }

  /**
   * Checks that the arguments passed into the command line can be parsed into their specified
   * types.
   *
   * @param type Datatype of argument.
   * @param value Value of argument being passed in.
   */
  private void checkArgumentsForTypeEquivalence(Datatype type, String value) {
    if (type == Datatype.FLOAT) {
      try {
        Float.parseFloat(value);
      } catch (NumberFormatException e) {
        throw new IllegalTypeException(value, "float");
      }
    } else if (type == Datatype.INTEGER) {
      try {
        Integer.parseInt(value);
      } catch (NumberFormatException e) {
        throw new IllegalTypeException(value, "integer");
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
        throw new DiscreteValueException(
            argumentName, values, arguments.get(argumentName).discreteValues);
      }
    }
  }

  /**
   * Checks discrete values are of proper type. If they are not of the correct type for the
   * argument, throw an exception.
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
          throw new IllegalTypeException(value, "float");
        }
      } else if (type == Datatype.INTEGER) {
        try {
          Integer.parseInt(value);
        } catch (NumberFormatException e) {
          throw new IllegalTypeException(value, "integer");
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

  /**
   * Checks if a shortform is a valid shortform.
   *
   * @param shortForm The shortform to be checked.
   * @return True or false if the shortform is valid.
   */
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
   * specified type. -If a named argument is declared but not followed by a value. Shortform
   * collections are exempt from this. After parse has been completed, getArgument can be called on
   * any values that appeared in the command line. If -h or --help appears in the command line,
   * parse halts execution and prints the help/usage message.
   *
   * @param args String array representation of command line values.
   */
  public void parse(String[] args) {
    if (detectHelp(args)) {
      throw new HelpException(constructHelpMessage());
    } else {
      Queue<String> q = new LinkedList<>(Arrays.asList(args));

      int position = 0;
      // While this queue has things in it.
      while (!q.isEmpty()) {
        String current = q.remove();
        // Is this an optional, whether long form, short form, or combined short form.
        if (current.startsWith("-")
            && (arguments.containsKey(current.substring(2))
                || !getLongForm(current.substring(1)).equals("")
                || checkCombinedShortForms(current.substring(1)))) {
          String value = q.peek();
          // If it is named long form
          if (current.substring(1, 2).equals("-")) {
            Datatype type = arguments.get(current.substring(2)).type;
            // If it is a boolean argument
            if (arguments.get(current.substring(2)).type == Datatype.BOOLEAN) {
              arguments.get(current.substring(2)).value = "true";
            } else {
              // Check that the argument has a value,
              if (q.isEmpty()) {
                throw new NoValueForOptionalException(current.substring(2));
              }
              checkArgumentsForTypeEquivalence(type, value);
              if ((q.peek().startsWith("--"))) {
                throw new NoValueForOptionalException(current.substring(2));
              }
              checkValueIsDiscrete(current.substring(2), value);
              // Remove from queue, set to value in the map.
              arguments.get(current.substring(2)).setValue(value);
              q.remove();
            }
            // If optional is named shortform
          } else {
            String longName = getLongForm(current.substring(1));
            // If this is a lone shortname flag or a boolean
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
                throw new NoValueForOptionalException(longName);
              }
              checkArgumentsForTypeEquivalence(type, value);
              if (((q.peek().startsWith("--") || q.peek().startsWith("-")))
                  && checkCombinedShortForms(q.peek())) {
                throw new NoValueForOptionalException(longName);
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
            throw new ExcessiveValuesException(excess);
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
        // argumentNameByPosition.get(position)
        throw new InsufficientValuesException(argumentNameByPosition.get(position));
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
    usageInfo = usageInfo + " [-h]";
    StringBuffer bufs = new StringBuffer();
    for (int i = 0; i < optionalArgument.size(); i++) {
      if (getEntry(optionalArgument.get(i)).shortName.isEmpty()) {
        if (getEntry(optionalArgument.get(i)).type == Datatype.BOOLEAN) {
          bufs.append(" [--" + optionalArgument.get(i) + "]");
        } else {
          bufs.append(
              " [--"
                  + optionalArgument.get(i)
                  + " "
                  + optionalArgument.get(i).toUpperCase(Locale.getDefault())
                  + "]");
        }
      } else {
        if (getEntry(optionalArgument.get(i)).type == Datatype.BOOLEAN) {
          bufs.append(" [-" + getEntry(optionalArgument.get(i)).shortName + "]");
        } else {
          bufs.append(
              " [-"
                  + getEntry(optionalArgument.get(i)).shortName
                  + " "
                  + optionalArgument.get(i).toUpperCase(Locale.getDefault())
                  + "]");
        }
      }
    }

    for (int i = 0; i < argumentNameByPosition.size(); i++) {
      bufs.append(" " + argumentNameByPosition.get(i));
    }
    usageInfo = usageInfo + bufs;

    String helpMessage = "usage: " + usageInfo + "\n\n" + programInfo + "\n\n";

    StringBuffer buffer = new StringBuffer();
    helpMessage += "positional arguments:\n";
    int largestWord = "-h, --help".length();
    for (Map.Entry<String, Argument> entry : arguments.entrySet()) {
      if (argumentNameByPosition.contains(entry.getKey()) == false) {
        String variable = "";
        if (entry.getValue().type == Datatype.BOOLEAN) {
          if (entry.getValue().shortName.length() > 0) {
            variable =
                "-"
                    + entry.getValue().shortName
                    + " "
                    + entry.getKey().toUpperCase(Locale.getDefault())
                    + ", "
                    + "--"
                    + entry.getKey()
                    + " "
                    + entry.getKey().toUpperCase(Locale.getDefault());
          } else {
            variable = "--" + entry.getKey();
          }

          if (variable.length() > largestWord) {
            largestWord = variable.length();
          }
        } else {
          if (entry.getValue().shortName.length() > 0) {
            variable =
                "-"
                    + entry.getValue().shortName
                    + " "
                    + entry.getKey().toUpperCase(Locale.getDefault())
                    + ", "
                    + "--"
                    + entry.getKey()
                    + " "
                    + entry.getKey().toUpperCase(Locale.getDefault());
          } else {
            variable = "--" + entry.getKey() + " " + entry.getKey();
          }
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
      } else if (arguments.get(argumentNameByPosition.get(i)).type == Datatype.BOOLEAN) {
        type = "";
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

    for (int i = 0; i < optionalArgument.size(); i++) {
      String variable = "";
      Argument entry = arguments.get(optionalArgument.get(i));
      String name = optionalArgument.get(i);
      spaces = largestWord + 2;
      if (entry.type == Datatype.BOOLEAN) {
        if (entry.shortName.length() > 0) {
          variable = "-" + entry.shortName + ", " + "--" + name;

        } else {
          variable = "--" + name;
        }
        buffer.append("\n ");
        buffer.append(variable);
        spaces = spaces - variable.length();
        for (int j = 0; j < spaces; j++) {
          buffer.append(" ");
        }
        buffer.append(entry.help);
      } else {
        if (entry.shortName.length() > 0) {
          variable =
              "-"
                  + entry.shortName
                  + " "
                  + name.toUpperCase(Locale.getDefault())
                  + ", "
                  + "--"
                  + name
                  + " "
                  + name.toUpperCase(Locale.getDefault());
        } else {
          variable = "--" + name + " " + name.toUpperCase(Locale.getDefault());
        }
        buffer.append("\n ");
        buffer.append(variable);
        spaces = spaces - variable.length();
        for (int j = 0; j < spaces; j++) {
          buffer.append(" ");
        }
        spaces = 14;

        String type = "";
        if (arguments.get(name).type == Datatype.STRING) {
          type = "(string)";
        } else if (arguments.get(name).type == Datatype.INTEGER) {
          type = "(integer)";
        } else if (arguments.get(name).type == Datatype.FLOAT) {
          type = "(float)";
        }

        buffer.append(type);
        spaces = spaces - type.length();
        for (int j = 0; j < spaces; j++) {
          buffer.append(" ");
        }
        if (arguments.get(name).type == Datatype.BOOLEAN) {

        } else {
          buffer.append(entry.help + " ");
        }
        if (entry.discreteValues.length > 0) {
          buffer.append(entry.getDiscreteAsString());
        }
        buffer.append("(default: " + arguments.get(name).value + ")");
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
  private boolean detectHelp(String[] arguments) {
    for (int i = 0; i < arguments.length; i++) {
      if (arguments[i].equals("-h") || arguments[i].equals("--help")) {
        return true;
      }
    }
    return false;
  }
}
