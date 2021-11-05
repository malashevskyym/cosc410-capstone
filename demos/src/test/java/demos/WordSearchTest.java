package demos;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import org.junit.jupiter.api.Test;

public class WordSearchTest {
  @Test
  public void testWordSearch() {
    WordSearch test = new WordSearch();
    test.setBoard("softsweskaolzilklqmtreyoy");
    test.setWord("soft");
    String wordResult = test.findWord();
    assertEquals("soft", wordResult);
  }

  @Test
  public void testWordSearchWithHeightAndWidth() {
    WordSearch test = new WordSearch();
    test.setHeight(7);
    test.setWidth(6);
    test.setBoard("softwaeskqermilvcqputeromocertpuiopprogram");
    test.setWord("milk");
    String wordResult = test.findWord();
    assertEquals("milk", wordResult);
  }

  // @Test
  // public void testWordSearchWithHeight() {
  //   WordSearch test = new WordSearch();
  //   test.setHeight(4);
  //   test.setBoard("soptwerhomnioroeroer");
  //   test.setWord("sophomore");
  //   String wordResult = test.findWord();
  //   assertEquals("sophomore", wordResult);
  // }

  // @Test
  // public void testWordSearchWithWidth() {
  //   WordSearch test = new WordSearch();
  //   test.setWidth(4);
  //   test.setBoard("soptwerhomnioroeroer");
  //   test.setWord("noroom");
  //   String wordResult = test.findWord();
  //   assertEquals("noroom", wordResult);
  // }

  @Test
  public void testWordSearchCoordinates() {
    WordSearch test = new WordSearch();
    test.setHeight(7);
    test.setWidth(6);
    test.setBoard("softwaeskqermilvcqputeromocertpuiopprogram");
    test.setWord("milk");
    test.findWord();
    String wordResult = test.getFoundCoordinate();
    assertEquals("m:3,1 i:3,2 l:3,3 k:2,3", wordResult);
  }

  @Test
  public void testCoordinateNotFound() {
    WordSearch test = new WordSearch();
    test.setBoard("softsweskaolkilklqmtreyoy");
    test.setWord("pizza");
    test.findWord();
    String wordResult = test.getFoundCoordinate();
    assertEquals("pizza not found", wordResult);
  }
}
