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
    Assertions.assertThrows(
        HelpException.class,
        () -> {
          Test.parse(test1);
        });
  }

  @Test
  public void testOptionalWithHelpDifferentTypes() {
    LineParser Test = new LineParser();

    Test.addRequiredArgument("X", LineParser.Datatype.FLOAT, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.STRING, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.INTEGER, "Height");
    Test.addOptionalArgument("type", LineParser.Datatype.INTEGER, "Box");
    Test.addOptionalArgument("box", LineParser.Datatype.FLOAT, "Box");

    String[] test1 = new String[] {"2", "4", "7", "--type", "box", "--help"};

    Assertions.assertThrows(
        HelpException.class,
        () -> {
          Test.parse(test1);
        });
  }

  @Test
  public void testOptionalWithDashH() {
    LineParser Test = new LineParser();

    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.INTEGER, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.INTEGER, "Height");
    Test.addOptionalArgument("type", LineParser.Datatype.STRING, "Box");

    String[] test1 = new String[] {"2", "4", "7", "--type", "box", "-h"};
    Assertions.assertThrows(
        HelpException.class,
        () -> {
          Test.parse(test1);
        });
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
        ExcessiveValuesException.class,
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
        InsufficientValuesException.class,
        () -> {
          Test.parse(test1);
        });
  }

  @Test
  public void testDoubleDashAfterNamed() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.INTEGER, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.INTEGER, "Height");
    Test.addOptionalArgument("type", LineParser.Datatype.STRING, "Box");

    String[] test1 = new String[] {"2", "4", "--type", "--box"};
    Assertions.assertThrows(
        NoValueForOptionalException.class,
        () -> {
          Test.parse(test1);
        });
  }

  @Test
  public void testOptionalNoValue() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.INTEGER, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.INTEGER, "Height");
    Test.addOptionalArgument("type", LineParser.Datatype.STRING, "Box");

    String[] test1 = new String[] {"2", "4", "6", "--type"};
    Assertions.assertThrows(
        NoValueForOptionalException.class,
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
        IllegalTypeException.class,
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
        IllegalTypeException.class,
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
        IllegalTypeException.class,
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
        IllegalTypeException.class,
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
        IllegalTypeException.class,
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
        IllegalTypeException.class,
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
        IllegalTypeException.class,
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
        IllegalTypeException.class,
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

  @Test
  public void testShortNameString() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.FLOAT, "String to Float");
    Test.addOptionalArgument("type", Datatype.STRING, "box", "displays Type", "t");

    String[] test1 = new String[] {"2.0", "-t", "triangle"};
    Test.parse(test1);
    assertEquals("triangle", Test.getArgument("type"));
  }

  @Test
  public void testShortNameInt() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.FLOAT, "String to Float");
    Test.addOptionalArgument("type", Datatype.INTEGER, "5", "displays Type", "t");

    String[] test1 = new String[] {"2.0", "-t", "4"};
    Test.parse(test1);
    int value = Test.getArgument("type");
    assertEquals(4, value);
  }

  @Test
  public void testShortNameFloat() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.FLOAT, "String to Float");
    Test.addOptionalArgument("type", Datatype.FLOAT, "5.0", "displays Type", "t");

    String[] test1 = new String[] {"2.0", "-t", "4.0"};
    Test.parse(test1);
    Float f = (float) 4.0;
    Float value = Test.getArgument("type");
    assertEquals(f, value);
  }

  @Test
  public void testDiscreteValueString() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.FLOAT, "String to Float");
    Test.addOptionalArgument(
        "type",
        Datatype.STRING,
        "Box",
        "displays Type",
        "t",
        new String[] {"box", "triangle", "rectangle"});
    String[] test1 = new String[] {"2.0", "-t", "box"};
    Test.parse(test1);
    assertEquals("box", Test.getArgument("type"));
  }

  @Test
  public void testDiscreteValueStringFailsOptional() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.FLOAT, "String to Float");
    Test.addOptionalArgument(
        "type",
        Datatype.STRING,
        "Box",
        "displays Type",
        "t",
        new String[] {"box", "triangle", "rectangle"});
    String[] test1 = new String[] {"2.0", "-t", "5"};
    Assertions.assertThrows(
        DiscreteValueException.class,
        () -> {
          Test.parse(test1);
        });
  }

  @Test
  public void testDiscreteValueStringFailsRequired() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument(
        "X", LineParser.Datatype.INTEGER, "String to Float", new String[] {"1", "2", "3"});
    Test.addOptionalArgument("type", Datatype.STRING);
    String[] test1 = new String[] {"2.0", "-t", "5"};
    Assertions.assertThrows(
        IllegalTypeException.class,
        () -> {
          Test.parse(test1);
        });
  }

  @Test
  public void testDiscreteValueStringFailsRequiredInt() {
    LineParser Test = new LineParser();

    Assertions.assertThrows(
        IllegalTypeException.class,
        () -> {
          Test.addRequiredArgument(
              "X", LineParser.Datatype.INTEGER, "String to Float", new String[] {"1", "x", "3"});
          Test.addOptionalArgument("type", Datatype.STRING);
          String[] test1 = new String[] {"2.0", "-t", "5"};
          Test.parse(test1);
        });
  }

  @Test
  public void testDiscreteValueStringFailsRequiredFloat() {
    LineParser Test = new LineParser();

    Assertions.assertThrows(
        IllegalTypeException.class,
        () -> {
          Test.addRequiredArgument(
              "X", LineParser.Datatype.INTEGER, "String to Float", new String[] {"1", "x", "3"});
          Test.addOptionalArgument("type", Datatype.STRING);
          String[] test1 = new String[] {"2.0", "-t", "5"};
          Test.parse(test1);
        });
  }

  @Test
  public void testShortNameStringDashhShortForm() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.FLOAT, "String to Float");
    Test.addOptionalArgument("type", Datatype.BOOLEAN, "false", "displays Type", "t");

    String[] test1 = new String[] {"2.0", "-t", "-h"};
    Assertions.assertThrows(
        HelpException.class,
        () -> {
          Test.parse(test1);
        });
  }

  @Test
  public void testShortNameStringDashh() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.FLOAT, "String to Float");
    Test.addOptionalArgument("type", Datatype.BOOLEAN, "false", "displays Type");

    String[] test1 = new String[] {"2.0", "-t", "-h"};
    Assertions.assertThrows(
        HelpException.class,
        () -> {
          Test.parse(test1);
        });
  }

  @Test
  public void testMultipleShortForms() {
    LineParser Test = new LineParser();
    Test.addRequiredArgument("X", LineParser.Datatype.FLOAT, "String to Float");
    Test.addOptionalArgument("type", Datatype.BOOLEAN, "false", "displays Type", "t");
    Test.addOptionalArgument("variety", Datatype.BOOLEAN, "false", "displays Type", "v");

    String[] test1 = new String[] {"2.0", "-tv"};
    Test.parse(test1);
    boolean t = true;
    boolean f = Test.getArgument("type");
    assertEquals(f, t);
  }

  @Test
  public void addOptionalValueBoolean() {
    LineParser Test = new LineParser();

    Test.addOptionalArgument("type", Datatype.BOOLEAN);

    String[] test1 = new String[] {"--type"};
    Test.parse(test1);
    boolean t = true;
    boolean f = Test.getArgument("type");
    assertEquals(f, t);
  }

  @Test
  public void addOptionalValueInt() {
    LineParser Test = new LineParser();

    Test.addOptionalArgument("type", Datatype.INTEGER);

    String[] test1 = new String[] {"--type", "2"};
    Test.parse(test1);
    Integer t = 2;
    Integer f = Test.getArgument("type");
    assertEquals(t, f);
  }

  @Test
  public void addOptionalValueFloat() {
    LineParser Test = new LineParser();

    Test.addOptionalArgument("type", Datatype.FLOAT);

    String[] test1 = new String[] {"--type", "2.0"};
    Test.parse(test1);
    Float t = (float) 2.0;
    Float f = Test.getArgument("type");
    assertEquals(t, f);
  }

  // @Test
  // public void testXMLnamed() {
  // LineParser Test = new LineParser();
  // Test.addArgsFromFile("file.xml");
  // Integer testValue = Test.getArgument("precision");
  // assertEquals(4, testValue);
  // }
}
