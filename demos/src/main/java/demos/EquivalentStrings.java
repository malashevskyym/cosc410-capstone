package demos;

import edu.wofford.woclo.*;
import java.util.LinkedHashSet;

public class EquivalentStrings {
  public static String message = "";
  public static String error = "";

  public static void main(String... args) {
    String helpMessage =
        "java EquivalentStrings [-h] string1 string2\n\nDetermine if two strings are equivalent.";
    LineParser parser = new LineParser(helpMessage);
    parser.addRequiredArgument("string1", LineParser.Datatype.STRING, "the first string");
    parser.addRequiredArgument("string2", LineParser.Datatype.STRING, "the second string");

    try {
      parser.parse(args);
      String stringOne = parser.getArgument("string1");
      String stringTwo = parser.getArgument("string2");
      if (isEquivalent(stringOne, stringTwo)) {
        message = "equivalent";
        System.out.println(message);
      } else {
        message = "not equivalent";
        System.out.println(message);
      }
    } catch (Exception e) {
      error = "EquivalentStrings error: " + e.getMessage();
      System.out.println(error);
    }
  }

  public EquivalentStrings() {}

  private static String convertStringToExpression(String s) {
    String expression = s;
    LinkedHashSet<String> uniqueChars = new LinkedHashSet<String>();

    for (int i = 0; i < s.length(); i++) {
      uniqueChars.add(String.valueOf(s.charAt(i)));
    }

    int uniqueCode = 0;
    for (String character : uniqueChars) {
      expression = expression.replace(character, String.valueOf(uniqueCode));
      uniqueCode++;
    }
    return expression;
  }

  private static boolean checkSameLength(String first, String second) {
    if (first.length() == second.length()) {
      return true;
    }
    return false;
  }

  public static boolean isEquivalent(String first, String second) {
    String convertedFirst = convertStringToExpression(first);
    String convertedSecond = convertStringToExpression(second);
    if (checkSameLength(first, second) && convertedFirst.equals(convertedSecond)) {
      return true;
    }
    return false;
  }
}
