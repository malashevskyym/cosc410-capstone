package demos;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import org.junit.jupiter.api.Test;

public class OverlappingRectanglesTest {

  @Test
  public void testRectanglesOverlap() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream console = System.out;
    try {
      try {
        System.setOut(new PrintStream(bytes, false, "UTF-8"));
        String[] commandLineArgs;
        commandLineArgs = new String[] {"2", "2", "4", "4", "3", "3", "6", "5"};
        OverlappingRectangles.main(commandLineArgs);
      } catch (UnsupportedEncodingException e) {
        throw new AssertionError("UTF-8 is unknown");
      }
    } finally {
      System.setOut(console);
    }
    String str = "";
    try {
      str = bytes.toString("UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new AssertionError("UTF-8 is unknown");
    }
    assertEquals("1 9", str);
  }

  @Test
  public void testRectanglesOverlap2() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream console = System.out;
    try {
      try {
        System.setOut(new PrintStream(bytes, false, "UTF-8"));
        String[] commandLineArgs;
        commandLineArgs = new String[] {"2", "2", "4", "4", "1", "2", "4", "3"};
        OverlappingRectangles.main(commandLineArgs);
      } catch (UnsupportedEncodingException e) {
        throw new AssertionError("UTF-8 is unknown");
      }
    } finally {
      System.setOut(console);
    }
    String str = "";
    try {
      str = bytes.toString("UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new AssertionError("UTF-8 is unknown");
    }
    assertEquals("2 5", str);
  }

  @Test
  public void testRectanlgesDoNotOverlap() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream console = System.out;
    try {
      try {
        System.setOut(new PrintStream(bytes, false, "UTF-8"));
        String[] commandLineArgs;
        commandLineArgs = new String[] {"2", "2", "4", "4", "5", "3", "8", "5"};
        OverlappingRectangles.main(commandLineArgs);
      } catch (UnsupportedEncodingException e) {
        throw new AssertionError("UTF-8 is unknown");
      }
    } finally {
      System.setOut(console);
    }
    String str = "";
    try {
      str = bytes.toString("UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new AssertionError("UTF-8 is unknown");
    }
    assertEquals("0 10", str);
  }

  @Test
  public void testRectanlgesDoNotOverlapDashHelp() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream console = System.out;
    try {
      try {
        System.setOut(new PrintStream(bytes, false, "UTF-8"));
        String[] commandLineArgs;
        commandLineArgs = new String[] {"2", "2", "4", "4", "5", "3", "8", "5", "-h"};
        OverlappingRectangles.main(commandLineArgs);
      } catch (UnsupportedEncodingException e) {
        throw new AssertionError("UTF-8 is unknown");
      }
    } finally {
      System.setOut(console);
    }
    String str = "";
    try {
      str = bytes.toString("UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new AssertionError("UTF-8 is unknown");
    }
    assertEquals(
        "usage: java OverlappingRectangles [-h] x1 y1 x2 y2 x3 y3 x4 y4 \n Determine the overlap and total area of two rectangles.\npositional arguments:\nx1          (integer)     lower-left x for rectangle 1\ny1          (integer)     lower-left y for rectangle 1\nx2          (integer)     upper-right x for rectangle 1\ny2          (integer)     upper-right y for rectangle 1\nx3          (integer)     lower-left x for rectangle 2\ny3          (integer)     lower-left y for rectangle 2\nx4          (integer)     upper-right x for rectangle 2\ny4          (integer)     upper-right y for rectangle 2\n\nnamed arguments:\n-h, --help  show this help message and exit",
        str);
  }
}
