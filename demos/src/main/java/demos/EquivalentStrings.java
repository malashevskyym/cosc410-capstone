package demos;

import edu.wofford.woclo.*;
import java.util.LinkedHashSet;

public class EquivalentStrings {
  String message = "";

  public static void main(String... args) {
    LineParser parser =
        new LineParser("java EquivalentStrings", "Determine if two strings are equivalent.");
    parser.addRequiredArgument("string1", LineParser.Datatype.STRING, "the first string");
    parser.addRequiredArgument("string2", LineParser.Datatype.STRING, "the second string");

    try {
      parser.parse(args);
      EquivalentStrings equivalentStrings =
          new EquivalentStrings(parser.getArgument("string1"), parser.getArgument("string2"));

      System.out.println(equivalentStrings.message);
    } catch (Exception e) {
      System.out.println("EquivalentStrings error: " + e.getMessage());
    }
  }

  public EquivalentStrings(String stringOne, String stringTwo) {
    message = checkIfEquivalent(stringOne, stringTwo);
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

  private String checkIfEquivalent(String first, String second) {
    String convertedFirst = convertStringToExpression(first);
    String convertedSecond = convertStringToExpression(second);
    if (checkSameLength(first, second) && convertedFirst.equals(convertedSecond)) {
      return "equivalent";
    }
    return "not equivalent";
  }
}
