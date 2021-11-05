package demos;

import edu.wofford.woclo.*;
import java.util.LinkedHashSet;

public class EquivalentStrings {

  public static void main(String... args) {
    String helpMessage =
        "This program determines if two strings are 'equivalent'. Equivalent means one string can be transformed into the other by substituting one letter for another consistently.";
    LineParser parser = new LineParser(helpMessage);
    parser.addRequiredArgument("first", LineParser.Datatype.STRING, "The first string");
    parser.addRequiredArgument("second", LineParser.Datatype.STRING, "The second string");

    parser.parse(args);

    EquivalentStrings execute = new EquivalentStrings();
    if (execute.isEquivalent(parser.getArgument("first"), parser.getArgument("second"))) {
      System.out.println("equivalent");
    } else {
      System.out.println("not equivalent");
    }
  }

  public EquivalentStrings() {}

  private String convertStringToExpression(String s) {
    String expression = s;
    int uniqueCode = 0;
    LinkedHashSet<String> uniqueChars = new LinkedHashSet<String>();
    for (int i = 0; i < s.length(); i++) {
      uniqueChars.add(String.valueOf(s.charAt(i)));
    }
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

  public boolean isEquivalent(String first, String second) {
    String convertedFirst = convertStringToExpression(first);
    String convertedSecond = convertStringToExpression(second);
    if (!checkSameLength(first, second) && convertedFirst.equals(convertedSecond)) {
      return true;
    }
    return false;
  }
}
