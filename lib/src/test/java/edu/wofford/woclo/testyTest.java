package edu.wofford.woclo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

public class testyTest {

  @Test
  public void testLineParserConstructorNoArguments() {
    lineParser test = new lineParser(0, new String[] {});
    int testLen = test.getArgs().length;
    assertEquals(0, testLen);
  }

  @Test
  public void testLineParserNotEmpty() {
    lineParser test = new lineParser(2, new String[] { "Testing1", "Testing2" });
    int testLen = test.getArgs().length;
    assertEquals(2, testLen);
  }

  // These don't quite work yet
  @Test
  public void testLineParserLessArgsThanLength() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new lineParser(3, new String[] { "Testing1", "Testing2" });
    });
  }

  @Test
  public void testLineParserMoreArgsThanLength() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new lineParser(2, new String[] { "Testing1", "Testing2", "Testing3" });
    });
  }
}
