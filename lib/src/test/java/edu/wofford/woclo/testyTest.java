package edu.wofford.woclo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

public class TestyTest {

  @Test
  public void testLineParserConstructorNoArguments() {
    LineParser test = new LineParser(0, new String[] {});
    int testLen = test.getArgs().length;
    assertEquals(0, testLen);
  }

  @Test
  public void testLineParserNotEmpty() {
    LineParser test = new LineParser(2, new String[] {"Testing1", "Testing2"});
    int testLen = test.getArgs().length;
    assertEquals(2, testLen);
  }

  @Test
  public void testLineParserGetHelp() {
    LineParser test =
        new LineParser(2, new String[] {"Testing1", "Testing2"}, "this is a help message");
    assertEquals(test.getHelpMessage(), "this is a help message");
  }

  // These don't quite work yet
  @Test
  public void testLineParserLessArgsThanLength() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          new LineParser(3, new String[] {"Testing1", "Testing2"});
        });
  }

  @Test
  public void testLineParserLessArgsThanLengthWithH() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          new LineParser(3, new String[] {"Testing1", "Testing2"}, "help");
        });
  }

  @Test
  public void testLineParserMoreArgsThanLengthWithH() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          new LineParser(1, new String[] {"Testing1", "Testing2"}, "help");
        });
  }

  @Test
  public void testLineParserMoreArgsThanLength() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          new LineParser(2, new String[] {"Testing1", "Testing2", "Testing3"});
        });
  }

  @Test
  public void testSetArgsWithHelp() {
    LineParser test =
        new LineParser(3, new String[] {"Testing1", "Testing2", "--help"}, "Help Info");

    assertEquals(test.getHelpMessage(), "Help Info");
  }

  @Test
  public void testSetArgsWithHelpWithoutHelpMessage() {
    LineParser test = new LineParser(3, new String[] {"Testing1", "Testing2", "-h"});

    assertEquals(test.getHelpMessage(), "");
  }

  @Test
  public void testSetArgs() {
    LineParser test = new LineParser(2, new String[] {"Testing1", "Testing2"});
    test.setArgs(new String[] {"Testing3", "Testing4"});
    assertEquals(test.getArgs()[0], "Testing3");
  }

  @Test
  public void testSetArgs2() {
    LineParser test = new LineParser(2, new String[] {"Testing1", "Testing2"});
    test.setArgs(new String[] {"Testing3", "Testing4"});
    assertEquals(test.getArgs()[1], "Testing4");
  }

  @Test
  public void testGetArgsArray() {
    LineParser test = new LineParser(2, new String[] {"Testing1", "Testing2"});
    String tester = test.getArgsArray(0);
    assertEquals(tester, "Testing1");
  }

  @Test
  public void testGetArgsArray2() {
    LineParser test = new LineParser(2, new String[] {"Testing1", "Testing2"});
    String tester = test.getArgsArray(1);
    assertEquals(tester, "Testing2");
  }

  @Test
  public void testGetPosition() {
    LineParser test = new LineParser(2, new String[] {"Testing1", "Testing2"});
    Integer tester = test.getPosition("Testing1");
    assertEquals(tester, 0);
  }

  @Test
  public void testGetPosition2() {
    LineParser test = new LineParser(2, new String[] {"Testing1", "Testing2"});
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          test.getPosition("xyz");
        });
  }
}
