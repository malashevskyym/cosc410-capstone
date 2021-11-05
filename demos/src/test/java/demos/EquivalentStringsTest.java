package demos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EquivalentStringsTest {
  @Test
  public void testCocoonAndZyzyyx() {
    EquivalentStrings test = new EquivalentStrings();
    test.main(new String[] {"cocoon", "zyzyyx"});

    assertEquals("equivalent", test.message);
  }

  @Test
  public void testBananaAndOrange() {
    EquivalentStrings test = new EquivalentStrings();
    test.main(new String[] {"banana", "orange"});

    assertEquals("not equivalent", test.message);
  }

  @Test
  public void testBongoAndDrum() {
    EquivalentStrings test = new EquivalentStrings();
    test.main(new String[] {"bongo", "drum"});

    assertEquals("not equivalent", test.message);
  }

  @Test
  public void testNoArguments() {
    EquivalentStrings test = new EquivalentStrings();
    test.main(new String[] {});

    assertEquals("EquivalentStrings error: the argument string1 is required", test.error);
  }

  @Test
  public void testTooFewArguments() {
    EquivalentStrings test = new EquivalentStrings();
    test.main(new String[] {"abc"});

    assertEquals("EquivalentStrings error: the argument string2 is required", test.error);
  }

  @Test
  public void testTooManyArguments() {
    EquivalentStrings test = new EquivalentStrings();
    test.main(new String[] {"applebees", "bar", "and", "grill"});

    assertEquals("EquivalentStrings error: the value and matches no argument", test.error);
  }
}
