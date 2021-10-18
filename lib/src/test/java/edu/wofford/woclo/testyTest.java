package edu.wofford.woclo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;

public class testyTest {

  @Test
  public void testF1NoArguments() {
    // Simulating passing arguments into main
    lineParser test = new lineParser(new String[] {});
    int ln;
    if (test.returnHashtable().isEmpty()) {
      ln = 0;
    } else {
      ln = test.returnHashtable().size();
    }
    assertEquals(0, ln);
  }

  @Test
  public void testF1IsNotEmpty() {
    // Simulating passing arguments into main
    lineParser test = new lineParser(new String[] {"testing"});
    int ln;
    if (test.returnHashtable().isEmpty()) {
      ln = 0;
    } else {
      ln = test.returnHashtable().size();
    }
    assertEquals(1, ln);
  }
  // Nothing, one, isString

  @Test
  public void testF1GetValuesFromIdentifiers() {
    lineParser test = new lineParser(new String[] {"testing"});
    String value = test.GetValuesFromIdentifier("0");
    assertEquals("Testing", value);
  }

  @Test
  public void testF2HasDashHelp() {

    lineParser test = new lineParser(new String[] {"testing", "--help"});

    boolean hasHelp = test.detectHelp();

    assertTrue(hasHelp);
  }

  @Test
  public void testF2DoesNotHaveDashHelp() {

    lineParser test = new lineParser(new String[] {"testing"});

    boolean hasHelp = test.detectHelp();
    assertFalse(hasHelp);
  }

  @Test
  public void testF2HasDashH() {

    lineParser test = new lineParser(new String[] {"cat", "bat", "quagmire", "-h"});

    boolean hasHelp = test.detectHelp();

    assertTrue(hasHelp);
  }

  @Test
  public void testArgumentDescriptionInserted() {
    lineParser test = new lineParser(new String[] {"cat", "bat", "quagmire", "-h"});
    test.addDescription("quagmire", "This is a description");

    assertEquals(test.argsDescriptive.get("quagmire"), "This is a description");
  }

  @Test
  public void testArgumentDescription() {
    lineParser test = new lineParser(new String[] {"cat", "bat", "quagmire", "-h"});
    test.addDescription("quagmire", "This is a description");

    assertEquals(test.returnDescription(), "This is a description");
  }
}
