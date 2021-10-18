package edu.wofford.woclo;

import java.util.*;

public class lineParser {
  static String[] args = new String[] {};
  int numArgs = 0;
  String helpInfo = "";

  /**
   * Constructor lineParser
   *
   * <p>Creates an instance of the mainArgs Hashtable that contains a key and the arguments passed
   * in the command line.
   */
  public lineParser(int numArgs, String[] args) {
    if (detectHelp()) {
      System.out.println(helpInfo);
      System.exit(1);
    }
    args = new String[numArgs];
    if (numArgs > args.length) {
      // Throw exception

      throw new IllegalArgumentException("Too many arguments");

    } else if (numArgs < args.length) {
      // Throw exception
      throw new IllegalArgumentException("Too few arguments");
    }

    this.args = args;
    this.numArgs = numArgs;
  }

  public lineParser(int numArgs, String[] args, String helpInfo) {
    if (detectHelp()) {
      System.out.println(helpInfo);
      System.exit(1);
    }
    args = new String[numArgs];
    if (numArgs > args.length) {
      // Throw exception

      throw new IllegalArgumentException("Too many arguments");

    } else if (numArgs < args.length) {
      // Throw exception
      throw new IllegalArgumentException("Too few arguments");
    }
    this.helpInfo = helpInfo;
    this.args = args;
    this.numArgs = numArgs;
  }

  public String[] getArgs() {
    return this.args;
  }

  public void setArgs(String[] args) {
    this.args = args;
  }

  /**
   * Detects if the argmuments in command line contain a --help or -h.
   *
   * @return Returns boolean true if args does contain --help or -h.
   */
  private static boolean detectHelp() {
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("--help") || args[i].equals("-h")) {
        return true;
      }
    }
    return false;
  }
}
