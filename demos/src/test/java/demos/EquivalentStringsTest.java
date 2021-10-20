package demos;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import org.junit.jupiter.api.Test;

class EquivalentStringsTest {
  @Test
  public void testEquivalentStringsNoArguments() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream console = System.out;
    try {
      try {
        System.setOut(new PrintStream(bytes, false, "UTF-8"));
        EquivalentStrings.main();
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
    assertEquals("EquivalentStrings error: the argument string1 is required", str);
  }

  @Test
  public void testEquivalentStringsOneArgument() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream console = System.out;
    try {
      try {
        System.setOut(new PrintStream(bytes, false, "UTF-8"));
        EquivalentStrings.main("argument");
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
    assertEquals("EquivalentStrings error: the argument string2 is required", str);
  }

  @Test
  public void testEquivalentStringsMoreThanTwoArguments() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream console = System.out;
    try {
      try {
        System.setOut(new PrintStream(bytes, false, "UTF-8"));
        String[] commandLineArgs;
        commandLineArgs = new String[] {"argument1", "argument2", "argument3"};
        EquivalentStrings.main(commandLineArgs);
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
    assertEquals("EquivalentStrings error: the value argument3 matches no argument", str);
  }

  @Test
  public void testEquivalentStringsCocoonAndXyxyyz() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream console = System.out;
    try {
      try {
        System.setOut(new PrintStream(bytes, false, "UTF-8"));
        String[] commandLineArgs;
        commandLineArgs = new String[] {"cocoon", "xyxyyz"};
        EquivalentStrings.main(commandLineArgs);
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
    assertEquals("equivalent", str);
  }

  @Test
  public void testEquivalentStringsBananaAndOrange() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream console = System.out;
    try {
      try {
        System.setOut(new PrintStream(bytes, false, "UTF-8"));
        String[] commandLineArgs;
        commandLineArgs = new String[] {"banana", "orange"};
        EquivalentStrings.main(commandLineArgs);
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
    assertEquals("not equivalent", str);
  }

  @Test
  public void testEquivalentStringsBridgeAndXylophone() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream console = System.out;
    try {
      try {
        System.setOut(new PrintStream(bytes, false, "UTF-8"));
        String[] commandLineArgs;
        commandLineArgs = new String[] {"bridge", "xylophone"};
        EquivalentStrings.main(commandLineArgs);
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
    assertEquals("not equivalent", str);
  }
}
