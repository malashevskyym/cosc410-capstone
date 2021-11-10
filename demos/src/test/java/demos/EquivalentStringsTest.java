package demos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EquivalentStringsTest {
  @Test
  public void testCocoonAndZyzyyx() {
    String[] testStrings = new String[] {"cocoon", "zyzyyx"};
    EquivalentStrings test = new EquivalentStrings(testStrings[0], testStrings[1]);
    test.main(testStrings);

    assertEquals("equivalent", test.checkIfWorks(testStrings));
  }

  @Test
  public void testBananaAndOrange() {
    String[] testStrings = new String[] {"banana", "orange"};
    EquivalentStrings test = new EquivalentStrings(testStrings[0], testStrings[1]);
    test.main(testStrings);

    assertEquals("not equivalent", test.checkIfWorks(testStrings));
  }

  @Test
  public void testBongoAndDrum() {
    String[] testStrings = new String[] {"bongo", "drum"};
    EquivalentStrings test = new EquivalentStrings(testStrings[0], testStrings[1]);
    test.main(testStrings);

    assertEquals("not equivalent", test.checkIfWorks(testStrings));
  }

  @Test
  public void testNoArguments() {
    String[] testStrings = new String[] {};
    EquivalentStrings test = new EquivalentStrings(testStrings[0], testStrings[1]);
    test.main(testStrings);

    assertEquals(
        "EquivalentStrings error: the argument string1 is required",
        test.checkIfWorks(testStrings));
  }

  @Test
  public void testTooFewArguments() {
    String[] testStrings = new String[] {"abc"};
    EquivalentStrings test = new EquivalentStrings(testStrings[0], testStrings[1]);
    test.main(testStrings);

    assertEquals(
        "EquivalentStrings error: the argument string2 is required",
        test.checkIfWorks(testStrings));
  }

  @Test
  public void testTooManyArguments() {
    String[] testStrings = new String[] {"applebees", "bar", "and", "grill"};
    EquivalentStrings test = new EquivalentStrings(testStrings[0], testStrings[1]);
    test.main(testStrings);

    assertEquals(
        "EquivalentStrings error: the value and matches no argument",
        test.checkIfWorks(testStrings));
  }
}
