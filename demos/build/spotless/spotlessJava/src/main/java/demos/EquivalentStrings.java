package demos;

import edu.wofford.woclo.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class EquivalentStrings {
  protected String string1;
  protected ArrayList<String> string1ListChars = new ArrayList<String>();
  protected String string2;
  protected ArrayList<String> string2ListChars = new ArrayList<String>();

  public static void main(String... args) {
    int length = args.length;
    LineParser lineParser = new LineParser(length, args);
    String firstString = args[0];
    String secondString = args[1];
    EquivalentStrings equivalentStrings = new EquivalentStrings(firstString, secondString);
    equivalentStrings.isEquivalent();
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

  protected void convertStringToExpression(String string, HashSet<String> stringOfUniqueChars) {
    int expr = 0;
    for (String character : stringOfUniqueChars) {
      string.replace(character, String.valueOf(expr));
      expr++;
    }
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
