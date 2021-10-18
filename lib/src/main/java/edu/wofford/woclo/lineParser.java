package edu.wofford.woclo;

import java.util.*;

public class lineParser {
  static String[] args = new String[] {};
  Hashtable<String, String> mainArgs = new Hashtable<String, String>();
  Hashtable<String, String> argsDescriptive = new Hashtable<String, String>();

  /**
   * Constructor lineParser
   *
   * <p>Creates an instance of the mainArgs Hashtable that contains a key and the arguments passed
   * in the command line.
   */

  public lineParser(String[] args) {
    this.args = args;
    for (int i = 0; i < args.length; i++) {
      String iString = String.valueOf(i);
      mainArgs.put(iString, args[i]);
    }
  };

  /** Returns Hashtable mainArgs */
  public Hashtable<String, String> returnHashtable() {
    return mainArgs;
  }

  /**
   * Returns Value using the Identifier(key)
   *
   * @param identifier Identifier in the Hashtable
   */
  public String GetValuesFromIdentifier(String identifier) {
    return mainArgs.get(identifier);
  }

  public void setArgs(String[] args) {
    this.args = args;
  }

  /**
   * Detects if the argmuments in command line contain a --help or -h.
   *
   * @return Returns boolean true if args does contain --help or -h.
   */
  public static boolean detectHelp() {
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("--help") || args[i].equals("-h")) {
        return true;
      }
    }
    return false;
  }

  /**
   * Adds descriptive information to an argument via a hash table. Argment in string form will be
   * key and description will be value.
   *
   * @param argument Argument which user wants to add description to
   * @param description Additional desriptive information
   */
  protected void addDescription(String argument, String description) {
    if (argsDescriptive.contains(argument)) {
      argsDescriptive.replace(argument, description);
    } else {
      argsDescriptive.put(argument, description);
    }
  }

  /**
   * Returns descriptive information of the first argument that display additional information.
   *
   * @return A string with descriptive information of the first argument preceeeding a --help or
   *     --h.
   */
  protected String returnDescription() {
    if (detectHelp()) {
      // Whatever is before detect help should be selected, then its value called from
      // the description table
      int k = 0;
      // Finding position of argument before help
      for (int i = 0; i < args.length; i++) {
        if (args[i].equals("--help") || args[i].equals("-h")) {
          k = i;
          break;
        }
      }
      String s = (k - 1) + "";
      s = mainArgs.get(s);
      s = argsDescriptive.get(s);
      return s;
    }
    return "";
  }
}
