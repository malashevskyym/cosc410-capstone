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
  public void testLineParserMoreArgsThanLength() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          new LineParser(2, new String[] {"Testing1", "Testing2", "Testing3"});
        });
  }
}
