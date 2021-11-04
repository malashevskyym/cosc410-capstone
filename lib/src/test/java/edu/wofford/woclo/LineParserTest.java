package edu.wofford.woclo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wofford.woclo.LineParser.Datatype;
import java.util.Arrays;
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

    String[] test1 = new String[] {"2", " 4", "7", "--type", "box"};
    Test.parse(test1);
    assertTrue(Arrays.equals(new String[] {"X", "INT", "2", "Length"}, Test.getArg()[0]));
  }

  @Test
  public void testOptional() {
    LineParser Test = new LineParser();

    Test.addRequiredArgument("X", LineParser.Datatype.INTEGER, "Length");
    Test.addRequiredArgument("Y", LineParser.Datatype.INTEGER, "Width");
    Test.addRequiredArgument("Z", LineParser.Datatype.INTEGER, "Height");
    Test.addOptionalArgument("type", LineParser.Datatype.STRING);

    String[] test1 = new String[] {"2", " 4", "7", "--type", "box"};
    Test.parse(test1);
    assertTrue(Arrays.equals(new String[] {"type", "STRING", "box", ""}, Test.getArg()[3]));
  }
}
