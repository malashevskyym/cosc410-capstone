package edu.wofford.woclo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wofford.woclo.LineParser.Datatype;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LineParserTest {

  @Test
  public void testRequiredNoHelp() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("Test1", Datatype.STRING);
    assertEquals("Test1", Test.getArg()[0][0]);
  }

  @Test
  public void testNoOptional() {
    LineParser Test = new LineParser("This program calculates trajectory");

    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.INTEGER, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.INTEGER, "Height");

    String[] test1 = new String[] {"2", "4", "7"};
    Test.parse(test1);
    assertTrue(Arrays.equals(new String[] {"X", "INT", "2", "Length"}, Test.getArg()[0]));
  }

  @Test
  public void testOptionalWithDesc() {
    LineParser Test = new LineParser("This program calculates trajectory");

    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.INTEGER, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.INTEGER, "Height");
    Test.addOptionalArgument("type", LineParser.Datatype.STRING, "Box", "Shape");

    String[] test1 = new String[] {"2", "4", "7", "--type", "box"};
    Test.parse(test1);
    assertTrue(Arrays.equals(new String[] {"type", "STRING", "box", "Shape"}, Test.getArg()[3]));
  }

  @Test
  public void testOptionalWithFloat() {
    LineParser Test = new LineParser("This program calculates trajectory");

    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.INTEGER, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.FLOAT, "Height");
    Test.addOptionalArgument("type", LineParser.Datatype.STRING, "Shape");

    String[] test1 = new String[] {"2", "4", "7", "--type", "box"};
    Test.parse(test1);
    assertTrue(Arrays.equals(new String[] {"Z", "FLOAT", "7", "Height"}, Test.getArg()[2]));
  }

  @Test
  public void testOptional() {
    LineParser Test = new LineParser();

    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.INTEGER, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.INTEGER, "Height");
    Test.addOptionalArgument("type", LineParser.Datatype.STRING, "Box");

    String[] test1 = new String[] {"2", "4", "7", "--type", "box"};
    Test.parse(test1);
    assertTrue(Arrays.equals(new String[] {"type", "STRING", "box", ""}, Test.getArg()[3]));
  }

  @Test
  public void testOptionalAfterOptional() {
    LineParser Test = new LineParser();

    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.INTEGER, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.INTEGER, "Height");
    Test.addOptionalArgument("type", LineParser.Datatype.STRING, "Box");
    Test.addOptionalArgument("area", LineParser.Datatype.INTEGER, "15");

    String[] test1 = new String[] {"2", "4", "7", "--type", "box", "--area", "10"};
    Test.parse(test1);
    assertTrue(Arrays.equals(new String[] {"area", "INT", "10", ""}, Test.getArg()[0]));
  }

  @Test
  public void testOptionalWithHelp() {
    LineParser Test = new LineParser();

    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.INTEGER, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.INTEGER, "Height");
    Test.addOptionalArgument("type", LineParser.Datatype.STRING, "Box");

    String[] test1 = new String[] {"2", "4", "7", "--type", "box", "--help"};
    Test.parse(test1);
    assertTrue(Test.detectHelp(test1));
  }

  @Test
  public void testOptionalWithDashH() {
    LineParser Test = new LineParser();

    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.INTEGER, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.INTEGER, "Height");
    Test.addOptionalArgument("type", LineParser.Datatype.STRING, "Box");

    String[] test1 = new String[] {"2", "4", "7", "--type", "box", "-h"};
    Test.parse(test1);
    assertTrue(Test.detectHelp(test1));
  }

  @Test
  public void testTooManyArguments() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.INTEGER, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.INTEGER, "Height");
    Test.addOptionalArgument("type", LineParser.Datatype.STRING, "Box");

    String[] test1 = new String[] {"2", "4", "7", "6", "--type", "box"};
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          Test.parse(test1);
        });
  }

  @Test
  public void testTooFewArguments() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.INTEGER, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.INTEGER, "Height");
    Test.addOptionalArgument("type", LineParser.Datatype.STRING, "Box");

    String[] test1 = new String[] {"2", "4", "--type", "box"};
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          Test.parse(test1);
        });
  }

  @Test
  public void testConvertInt() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.INTEGER, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.INTEGER, "Height");
    Test.addOptionalArgument("type", LineParser.Datatype.STRING, "Box");

    String[] test1 = new String[] {"2", "z", "--type", "box"};
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          Test.parse(test1);
        });
  }

  @Test
  public void testConvertFloat() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.INTEGER, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.FLOAT, "Height");
    Test.addOptionalArgument("type", LineParser.Datatype.STRING, "Box");

    String[] test1 = new String[] {"2", "z", "--type", "box"};
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          Test.parse(test1);
        });
  }

  @Test
  public void testConvertFloatOptional() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.INTEGER, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.FLOAT, "Height");
    Test.addOptionalArgument("type", LineParser.Datatype.FLOAT, "Box");

    String[] test1 = new String[] {"2", "z", "--type", "box"};
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          Test.parse(test1);
        });
  }

  @Test
  public void testConvertIntOptional() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.INTEGER, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.FLOAT, "Height");
    Test.addOptionalArgument("type", LineParser.Datatype.INTEGER, "Box");

    String[] test1 = new String[] {"2", "z", "--type", "box"};
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          Test.parse(test1);
        });
  }

  @Test
  public void testConvertFloatOptional2() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.INTEGER, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.FLOAT, "Height");
    Test.addOptionalArgument("type", LineParser.Datatype.FLOAT, "Box");

    String[] test1 = new String[] {"2", "z", "--type", "box"};
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          Test.parse(test1);
        });
  }

  @Test
  public void testNamedNoValue() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.INTEGER, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.FLOAT, "Height");
    Test.addOptionalArgument("type", LineParser.Datatype.INTEGER, "Box");

    String[] test1 = new String[] {"2", "z", "--type", "--box"};
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          Test.parse(test1);
        });
  }

  @Test
  public void testNamedNoValue2() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.INTEGER, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.FLOAT, "Height");
    Test.addOptionalArgument("type", LineParser.Datatype.INTEGER, "Box");

    String[] test1 = new String[] {"2", "z", "--type", "-box"};
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          Test.parse(test1);
        });
  }

  @Test
  public void testNamedValueFloat() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.INTEGER, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.FLOAT, "Height");
    Test.addOptionalArgument("type", LineParser.Datatype.FLOAT, "Box");

    String[] test1 = new String[] {"2", "3", "4.0", "--type", "box"};
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          Test.parse(test1);
        });
  }

  @Test
  public void testParseToString() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.STRING, "String to String");

    String[] test1 = new String[] {"Hello"};
    Test.parse(test1);
    assertEquals("Hello", Test.getArgument("X"));
  }

  @Test
  public void testParseToInteger() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "String to Integer");

    String[] test1 = new String[] {"11"};
    Test.parse(test1);
    assertEquals((Integer) 11, Test.getArgument("X"));
  }

  @Test
  public void testParseToFloat() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.FLOAT, "String to Float");

    String[] test1 = new String[] {"3.15"};
    Test.parse(test1);
    assertEquals((Float) 3.15f, Test.getArgument("X"));
  }
}
