package demos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EquivalentStringsTest {
  @Test
  public void testCocoonAndZyzyyx() {
    String[] testStrings = new String[] {"cocoon", "zyzyyx"};
    EquivalentStrings test = new EquivalentStrings(testStrings[0], testStrings[1]);

    assertEquals("equivalent", test.message);
  }

  @Test
  public void testBananaAndOrange() {
    String[] testStrings = new String[] {"banana", "orange"};
    EquivalentStrings test = new EquivalentStrings(testStrings[0], testStrings[1]);

    assertEquals("not equivalent", test.message);
  }

  @Test
  public void testBongoAndDrum() {
    String[] testStrings = new String[] {"bongo", "drum"};
    EquivalentStrings test = new EquivalentStrings(testStrings[0], testStrings[1]);

    assertEquals("not equivalent", test.message);
  }

  @Test
  public void testEquivalentStringsMain() {
    String[] testStrings = new String[] {"hello", "brook"};
    EquivalentStrings test = new EquivalentStrings(testStrings[0], testStrings[1]);
    test.main(testStrings);

    assertEquals("equivalent", test.message);
  }

  @Test
  public void testEquivalentStringsMainException() {
    String[] testStrings = new String[] {"brook", "hello"};
    EquivalentStrings test = new EquivalentStrings(testStrings[0], testStrings[1]);
    test.main("George", "Washing", "Tons");

    assertEquals("equivalent", test.message);
  }
}
