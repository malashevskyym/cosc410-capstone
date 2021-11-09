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

  @Test
  public void testWordSearchCoordinatesSoft() {
    WordSearch test = new WordSearch();
    test.setBoard("softsweskaolzilklqmtreyoy");
    test.setWord("soft");
    test.findWord();
    String wordResult = test.getFoundCoordinate();
    assertEquals("s:1,1 o:1,2 f:1,3 t:1,4", wordResult);
  }

  @Test
  public void testWordSearchCoordinatesSenior() {
    WordSearch test = new WordSearch();
    test.setHeight(4);
    test.setBoard("soptwerhomnioroeroer");
    test.setWord("senior");
    test.findWord();
    String wordResult = test.getFoundCoordinate();
    assertEquals("s:1,1 e:2,1 n:3,1 i:3,2 o:3,3 r:3,4", wordResult);
  }

  @Test
  public void testWordgetHeight() {
    WordSearch test = new WordSearch();
    test.setHeight(7);
    test.setWidth(6);
    test.setBoard("softwaeskqermilvcqputeromocertpuiopprogram");
    test.setWord("milk");
    int wordResult = test.getHeight();
    assertEquals(7, wordResult);
  }

  @Test
  public void testWordgetWidth() {
    WordSearch test = new WordSearch();
    test.setHeight(7);
    test.setWidth(6);
    test.setBoard("softwaeskqermilvcqputeromocertpuiopprogram");
    test.setWord("milk");
    int wordResult = test.getWidth();
    assertEquals(6, wordResult);
  }

  @Test
  public void testWordgetGrid() {
    WordSearch test = new WordSearch();
    test.setHeight(7);
    test.setWidth(6);
    test.setBoard("softwaeskqermilvcqputeromocertpuiopprogram");
    test.setWord("milk");
    String wordResult = test.getGrid();
    assertEquals("softwaeskqermilvcqputeromocertpuiopprogram", wordResult);
  }

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
  public void testWordSearchLookLeft() {
    WordSearch test = new WordSearch();
    test.setHeight(7);
    test.setWidth(6);
    test.setBoard("softwaeskqermilvcqputeromocertpuiopprogram");
    test.setWord("tfo");
    String wordResult = test.findWord();
    assertEquals("tfo", wordResult);
  }

  @Test
  public void testWordSearchNotFound() {
    WordSearch test = new WordSearch();
    test.setHeight(7);
    test.setWidth(6);
    test.setBoard("softwaeskqermilvcqputeromocertpuiopprogram");
    test.setWord("tzo");
    test.findWord();
    String wordResult = test.getFoundCoordinate();
    assertEquals("tzo not found", wordResult);
  }

  // @Test
  // public void testMain() {
  // WordSearch t = new WordSearch();
  // t.main(new String[] {"soptwerhomnioroeroer", "senior", "--height", "4"});
  // assertEquals("s:1,1 e:2,1 n:3,1 i:3,2 o:3,3 r:3,4", t.getFoundCoordinate());
  // }

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
