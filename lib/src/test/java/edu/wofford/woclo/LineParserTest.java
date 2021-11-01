package edu.wofford.woclo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class LineParserTest {

  @Test
  public void testLineParserNoArguments() {
    LineParser test = new LineParser(0, new String[] {});
    int length = test.getArgumentsAsStrings().length;
    assertEquals(0, length);
  }

  @Test
  public void testLineParserNotEmpty() {
    LineParser test = new LineParser(1, new String[] {"Testing"});
    // test.addIdentifier("t");
    int length = test.getArgumentsAsStrings().length;
    assertEquals(1, length);
  }

  @Test
  public void testLineParserFewerArgumentsThanIdentifiers() {
    LineParser test = new LineParser(2, new String[] {"Testing1"});
    // test.addIdentifier("t1");
    // test.addIdentifier("t2");
    assertEquals("Too few arguments", test.exceptionMessage);
  }

  @Test
  public void testLineParserMoreArgumentsThanIdentifiers() {
    LineParser test = new LineParser(1, new String[] {"Testing1", "Testing2"});
    // test.addIdentifier("t1");
    assertEquals("Too many arguments", test.exceptionMessage);
  }

  @Test
  public void testGetHelpMessage() {
    LineParser test = new LineParser(2, new String[] {"Testing1", "Testing2"});
    test.setProgramHelpMessage("This program is meant for testing.");
    // test.addIdentifier("t1", "Usage for t1");
    // test.addIdentifier("t2", "Usage for t2");
    assertEquals(
        "This program is meant for testing.\n\nUsage:\n  String  t1    - Usage for t1\n  String  t2    - Usage for t2\n",
        test.getHelpMessage());
  }

  @Test
  public void testCorrectArgumentsWithHelp() {
    LineParser test = new LineParser(2, new String[] {"Testing1", "Testing2", "--help"});
    test.setProgramHelpMessage("This program is meant for testing.");
    // test.addIdentifier("t1");
    // test.addIdentifier("t2");
    assertEquals(
        "This program is meant for testing.\n\nUsage:\n  String  t1\n  String  t2\n",
        test.getHelpMessage());
  }

  @Test
  public void testCorrectArgumentsWithH() {
    LineParser test = new LineParser(2, new String[] {"Testing1", "Testing2", "-h"});
    test.setProgramHelpMessage("This program is meant for testing.");
    // test.addIdentifier("t1");
    // test.addIdentifier("t2");
    assertEquals(
        "This program is meant for testing.\n\nUsage:\n  String  t1\n  String  t2\n",
        test.getHelpMessage());
  }

  @Test
  public void testIncorrectArgumentsWithHelp() {
    LineParser test = new LineParser(1, new String[] {"Testing1", "Testing2", "--help"});
    test.setProgramHelpMessage("This program is meant for testing.");
    // test.addIdentifier("t1");
    assertEquals(
        "This program is meant for testing.\n\nUsage\n  String  t1\n", test.getHelpMessage());
  }

  @Test
  public void testHelpWithoutProgramHelpMessage() {
    LineParser test = new LineParser(2, new String[] {"Testing1", "Testing2", "--help"});
    // test.addIdentifier("t1", "Usage for t1");
    // test.addIdentifier("t2", "Usage for t2");
    assertEquals(
        "Usage:\n  String  t1    - Usage for t1\n  String  t2    - Usage for t2\n",
        test.getHelpMessage());
  }

  @Test
  public void testDefaultAddIdentifier() {
    LineParser test = new LineParser(1, new String[] {"Testing1"});
    // test.addIdentifier("t1");
    assertEquals("Usage:\n  String  t1", test.getHelpMessage());
  }

  @Test
  public void testDescriptionAddIdentifier() {
    LineParser test = new LineParser(1, new String[] {"Testing1"});
    // test.addIdentifier("t1", "Usage for t1");
    assertEquals("Usage:\n  String t1    - Usage for t1\n", test.getHelpMessage());
  }

  @Test
  public void testTypeAddIdentifier() {
    LineParser test = new LineParser(3, new String[] {"Testing1", "17", "3.14"});
    // test.addIdentifier(LineParser.Datatype.STRING, "t1", "");
    // test.addIdentifier(LineParser.Datatype.INTEGER, "t2", "");
    // test.addIdentifier(LineParser.Datatype.FLOAT, "t3", "");
    assertEquals("Usage:\n  String  t1\n  Integer t2\n  Float   t3\n", test.getHelpMessage());
  }

  @Test
  public void testPositionalAddIdentifier() {
    LineParser test = new LineParser(3, new String[] {"Testing1", "Testing2", "Testing3"});
    // test.addIdentifier("t1");
    // test.addIdentifier("t2");
    // test.addIdentifier(LineParser.Datatype.STRING, "t3", "", 0);
    assertEquals("Usage:\n  String  t3\n  String  t1\n  String  t2\n", test.getHelpMessage());
  }

  @Test
  public void testRemoveIdentifier() {
    LineParser test = new LineParser(2, new String[] {"Testing1", "Testing2"});
    // test.addIdentifier("t1");
    // test.addIdentifier("t2");
    test.removeIdentifier("0");
    assertEquals("Usage:\n  String  1\n", test.getHelpMessage());
  }

  @Test
  public void testGetArgumentPosition() {
    LineParser test = new LineParser(2, new String[] {"Testing1", "Testing2"});
    // test.addIdentifier("t1");
    // test.addIdentifier("t2");
    assertEquals(1, test.getArgumentPosition("1"));
  }

  @Test
  public void testStringGetArgument() {
    LineParser test = new LineParser(2, new String[] {"Testing1, Testing2"});
    // test.addIdentifier(LineParser.Datatype.STRING, "t1", "");
    assertEquals(new String[] {"Testing1", "Testing2"}, test.getArgumentsAsStrings());
  }

  @Test
  public void testIntegerGetArgument() {
    LineParser test = new LineParser(2, new String[] {"17", "34"});
    // test.addIdentifier(LineParser.Datatype.INTEGER, "t1", "");
    assertEquals(new int[] {17, 34}, test.getArgumentsAsIntegers());
  }

  @Test
  public void testFloatGetArgument() {
    LineParser test = new LineParser(2, new String[] {"3.14", "6.62"});
    // test.addIdentifier(LineParser.Datatype.FLOAT, "t1", "");
    assertEquals(new float[] {(float) 3.14, (float) 6.62}, test.getArgumentsAsFloats());
  }
}
