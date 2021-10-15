package edu.wofford.woclo;

public class Feature2 {

  public Feature2() {}

  /**
   * Returns true if "-h" or "--help" is detected in arguement, false otherwise
   *
   * @param args String value of command line arguements
   * @return Boolean representing prescence of string value "--help" or "-h"
   */
  public static boolean detectHelp(String[] args) {
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("--help") || args[i].equals("-hi")) {
        return true;
      }
    }
    return false;
  }
}
