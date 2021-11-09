package demos;

import edu.wofford.woclo.*;
import java.util.LinkedHashSet;

public class EquivalentStrings {

  public static void main(String... args) {
    String helpMessage = "java EquivalentStrings [-h] string1 string2\n\nDetermine if two strings are equivalent.";
    LineParser parser = new LineParser(helpMessage);
    parser.addRequiredArgument("string1", LineParser.Datatype.STRING, "the first string");
    parser.addRequiredArgument("string2", LineParser.Datatype.STRING, "the second string");

    try {
      parser.parse(args);
      String[] values = new String[] {parser.getArgument("string1"), parser.getArgument("string2")};
      EquivalentStrings equivalentStrings = new EquivalentStrings(values);
    } catch (Exception e) {
      System.out.println("EquivalentStrings error: " + e.getMessage());
    }
  }

  public EquivalentStrings(String[] strings) {
    String string1 = strings[0];
    String string2 = strings[1];

    System.out.println(checkIfEquivalent(string1, string2));
  }

  public String checkIfEquivalent(String stringOne, String stringTwo) {
    if (isEquivalent(stringOne, stringTwo)) {
      return "equivalent";
    } else {
      return "not equivalent";
    }
  }

  public String checkIfWorks(String[] args) {
    LineParser parser = new LineParser();
    parser.addRequiredArgument("string1", LineParser.Datatype.STRING, "the first string");
    parser.addRequiredArgument("string2", LineParser.Datatype.STRING, "the second string");

    String message = "";
    try {
      parser.parse(args);
      if (isEquivalent(args[0], args[1])) {
        message = "equivalent";
      } else {
        message = "not equivalent";
      }
    } catch (Exception e) {
      message = "EquivalentStrings error: " + e.getMessage();
    }
    return message;
  }

  private String convertStringToExpression(String s) {
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

  private boolean checkSameLength(String first, String second) {
    if (first.length() == second.length()) {
      return true;
    }
    return false;
  }

  private boolean isEquivalent(String first, String second) {
    String convertedFirst = convertStringToExpression(first);
    String convertedSecond = convertStringToExpression(second);
    if (checkSameLength(first, second) && convertedFirst.equals(convertedSecond)) {
      return true;
    }
    return false;
  }
}
