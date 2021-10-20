package demos;

import edu.wofford.woclo.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class EquivalentStrings {
  protected String string1;
  protected ArrayList<String> string1ListChars = new ArrayList<String>();
  protected String string2;
  protected ArrayList<String> string2ListChars = new ArrayList<String>();

  public static void main(String... args) {
    int length = args.length;
    String helpMessage =
        "usage: java EquivalentStrings [-h] string1 string2\n\nDetermine if two strings are equivalent.\n\npositional arguments:\n string1     (string)      the first string\n string2     (string)      the second string\n\nnamed arguments:\n -h, --help  show this help message and exit";
    LineParser lineParser = new LineParser(length, args, helpMessage);
    String[] stringArgs = lineParser.getArgs();
    if (stringArgs.length == 0) {
      System.out.print("EquivalentStrings error: the argument string1 is required");
    } else if (stringArgs.length == 1) {
      System.out.print("EquivalentStrings error: the argument string2 is required");
    } else if (stringArgs.length > 2) {
      System.out.print(
          "EquivalentStrings error: the value " + stringArgs[2] + " matches no argument");
    } else {
      EquivalentStrings equivalentStrings = new EquivalentStrings(stringArgs[0], stringArgs[1]);
      equivalentStrings.isEquivalent();
    }
  }

  public EquivalentStrings(String string1, String string2) {
    this.string1 = string1;
    for (int i = 0; i < string1.length(); i++) {
      string1ListChars.add(Character.toString(string1.charAt(i)));
    }
    this.string2 = string2;
    for (int i = 0; i < string2.length(); i++) {
      string2ListChars.add(Character.toString(string2.charAt(i)));
    }
  }

  protected boolean checkSameLength() {
    if (string1.length() == string2.length()) {
      return true;
    }
    return false;
  }

  protected String convertStringToExpression(
      String string, LinkedHashSet<String> stringOfUniqueChars) {
    int expr = 0;
    String convertedString = string;
    for (String character : stringOfUniqueChars) {
      convertedString = convertedString.replace(character, String.valueOf(expr));
      expr++;
    }
    return convertedString;
  }

  protected void isEquivalent() {
    if (checkSameLength()) {
      LinkedHashSet<String> string1UniqueChars = new LinkedHashSet<String>(string1ListChars);
      String convertedString1 = convertStringToExpression(string1, string1UniqueChars);
      LinkedHashSet<String> string2UniqueChars = new LinkedHashSet<String>(string2ListChars);
      String convertedString2 = convertStringToExpression(string2, string2UniqueChars);
      if (convertedString1.equals(convertedString2)) {
        System.out.print("equivalent");
      } else {
        System.out.print("not equivalent");
      }
    } else {
      System.out.print("not equivalent");
    }
  }
}
