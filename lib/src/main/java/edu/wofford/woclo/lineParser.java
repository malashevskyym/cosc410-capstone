package edu.wofford.woclo;

public class LineParser {
  private String[] args = new String[] {};
  private String helpInfo = "";

  /**
   * Constructor lineParser
   *
   * <p>
   * Creates an instance of the mainArgs Hashtable that contains a key and the
   * arguments passed in the command line.
   *
   * @param numArgs is the number of arguments being passed in the command lin.
   * @param args    are the arguments that are being passed in the command line.
   */
  public LineParser(int numArgs, String[] args) {
    if (detectHelp()) {
      // Need to create new exception
      throw new HelpException(helpInfo);
    }

    if (numArgs > args.length) {
      // Throw exception

      throw new IllegalArgumentException("Too many arguments");

    } else if (numArgs < args.length) {
      // Throw exception
      throw new IllegalArgumentException("Too few arguments");
    }
    // Deep copy
    this.args = args.clone();
  }

  /**
   * Constructor lineParser.
   *
   * <p>
   * Creates an instance of the mainArgs Hashtable that contains a key and the
   * arguments passed in the command line.
   *
   * @param numArgs is the number of arguments being passed in the command line.
   * @param args    is the array of arguments that are being passed in the command
   *                line.
   * @param helpInf serves as an optional argument that stores a string of help
   *                info.
   */
  public LineParser(int numArgs, String[] args, String helpInfo) {

    if (detectHelp()) {

      throw new HelpException(helpInfo);
    }
    if (numArgs > args.length) {
      // Throw exception

      throw new IllegalArgumentException("Too many arguments");

    } else if (numArgs < args.length) {
      // Throw exception
      throw new IllegalArgumentException("Too few arguments");
    }
    this.helpInfo = helpInfo;
    this.args = args.clone();
  }

  public int getPosition(String argument) {
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals(argument)) {
        return i;
      }
    }
    throw new IllegalArgumentException();
  }

  public String getArgs(int position) {
    return args[position];
  }

  /**
   * Returns args array from lineParser class.
   *
   * @return Returns args array that is available in the lineParser class.
   */
  public String[] getArgs() {
    // Make a deep copy here
    String[] copy = args.clone();
    return copy;
  }

  /**
   * sets args array in the lineParse class to the args array being passed.
   *
   * @param args String array representing the command line arguments.
   */
  public void setArgs(String[] args) {
    // Deep copy here
    this.args = args.clone();
  }

  /**
   * Detects if the argmuments in command line contain a --help or -h.
   *
   * @return Returns boolean true if args does contain --help or -h.
   */
  public boolean detectHelp() {
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("--help") || args[i].equals("-h")) {
        return true;
      }
    }
    return false;
  }
}
