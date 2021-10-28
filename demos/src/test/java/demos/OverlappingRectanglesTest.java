package demos;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import org.junit.jupiter.api.Test;

public class OverlappingRectanglesTest {

  @Test
  public void TestRectanglesOverlap() {
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
  public void TestRectanglesOverlap2() {
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
  public void TestRectanlgesDoNotOverlap() {
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
}
