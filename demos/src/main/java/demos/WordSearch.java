package demos;

import edu.wofford.woclo.*;
import java.util.*;

public class WordSearch {

  int width = 5;
  int height = 5;
  char[][] board;
  String word = "";
  int row;
  int col;
  String wordBuild = "";
  String coordinatesString = "";
  String grid;

  public WordSearch() {}

  public void setHeight(int height) {
    this.height = height;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public void setBoard(String bd) {
    grid = bd;
    char[] temp = bd.toCharArray();
    board = new char[height][width];
    int index = 0;
    for (int i = 0; i < height; i++) {
      for (int t = 0; t < width; t++) {
        board[i][t] = temp[index++];
      }
    }
  }

  public String getGrid() {
    return grid;
  }

  private String setCoordinate(char a, int b, int c) {
    String temp = String.valueOf(a) + ":" + (b + 1) + "," + (c + 1);
    return temp;
  }

  private void findFirstLetter(int Iindex, int Tindex) {
    outerloop:
    for (int i = Iindex; i < height; i++) {
      if (i > Iindex) {
        Tindex = 0;
      }
      for (int t = Tindex; t < width; t++) {
        if (board[i][t] == word.charAt(0)) {
          row = i;
          col = t;
          String c2 = "" + setCoordinate(board[i][t], i, t);
          findNextLetter(1, i, t, "" + board[i][t], c2);
          break outerloop;
        }
      }
    }
  }

  public boolean lookRight(int index, int z, int y, String w1, String c1) {
    if (w1.contentEquals(word)) {
    } else if (y < width - 1 && board[z][y + 1] == word.charAt(index)) {
      String c2 = c1 + " " + setCoordinate(board[z][y + 1], z, y + 1);
      findNextLetter(index + 1, z, y + 1, w1 + board[z][y + 1], c2);
      return true;
    }
    return false;
  }

  public boolean lookLeft(int index, int z, int y, String w1, String c1) {
    if (w1.contentEquals(word)) {
    } else if (y > 0 && y <= width - 1 && board[z][y - 1] == word.charAt(index)) {
      String c2 = c1 + " " + setCoordinate(board[z][y - 1], z, y - 1);
      findNextLetter(index + 1, z, y - 1, w1 + board[z][y - 1], c2);
      return true;
    }
    return false;
  }

  public boolean lookDown(int index, int z, int y, String w1, String c1) {
    if (w1.contentEquals(word)) {
    } else if (z < height - 1 && board[z + 1][y] == word.charAt(index)) {
      String c2 = c1 + " " + setCoordinate(board[z + 1][y], z + 1, y);
      findNextLetter(index + 1, z + 1, y, w1 + board[z + 1][y], c2);
    } else if (z < height - 1 && board[z + 1][y] == word.charAt(index)) {
      String c2 = c1 + " " + setCoordinate(board[z + 1][y], z + 1, y);
      findNextLetter(index + 1, z + 1, y, w1 + board[z + 1][y], c2);
      return true;
    }
    return false;
  }

  public boolean lookUp(int index, int z, int y, String w1, String c1) {
    if (w1.contentEquals(word)) {
    } else if (z > 0 && z <= height - 1 && board[z - 1][y] == word.charAt(index)) {
      String c2 = c1 + " " + setCoordinate(board[z - 1][y], z - 1, y);
      findNextLetter(index + 1, z - 1, y, w1 + board[z - 1][y], c2);
      return true;
    }
    return false;
  }

  private void findNextLetter(int index, int z, int y, String w1, String c1) {
    System.out.println(w1 + " z: " + z + " y: " + y);
    if (w1.contentEquals(word)) {
      wordBuild = w1;
      coordinatesString = c1;
    } else {
      boolean up = lookUp(index, z, y, w1, c1);
      boolean down = lookDown(index, z, y, w1, c1);
      boolean right = lookRight(index, z, y, w1, c1);
      boolean left = lookLeft(index, z, y, w1, c1);
      if (!up && !down && !right && !left) {
        if (col == col - 1) {
          col = 0;
          findFirstLetter(row + 1, col);
        } else {
          findFirstLetter(row, col + 1);
        }
      }
    }
  }

  public String findWord() {
    findFirstLetter(0, 0);
    return wordBuild;
  }

  public String getFoundCoordinate() {
    String cString;
    if (!wordBuild.contains(word)) {
      cString = word + " not found";
    } else {
      cString = coordinatesString;
    }
    return cString;
  }

  public static void main(String[] args) {
    LineParser testLine =
        new LineParser(
            "java WordSearch [-h] [--width WIDTH] [--height HEIGHT] grid target",
            "Find a target word in a grid.");
    testLine.addRequiredArgument("grid", LineParser.Datatype.STRING, "the grid to search");
    testLine.addRequiredArgument("target", LineParser.Datatype.STRING, "the target word");
    testLine.addOptionalArgument("WIDTH", LineParser.Datatype.INTEGER, "5", "the grid width");
    testLine.addOptionalArgument("HEIGHT", LineParser.Datatype.INTEGER, "5", "the grid height");
    try {
      testLine.parse(args);
      WordSearch test = new WordSearch();
      test.setHeight(testLine.getArgument("HEIGHT"));
      test.setWidth(testLine.getArgument("WIDTH"));
      test.setBoard(testLine.getArgument("grid"));
      test.setWord(testLine.getArgument("target"));
      test.findWord();
      String wordResult = test.getFoundCoordinate();
      if (test.getHeight() * test.getWidth() != test.getGrid().length()) {
        System.out.println(
            "WordSearch error: "
                + "grid dimensions ("
                + test.getWidth()
                + " x "
                + test.getHeight()
                + ") do not match grid length ("
                + test.getGrid().length()
                + ")");
      } else {
        System.out.println(wordResult);
      }
    } catch (Exception e) {
      System.out.println("WordSearch error: " + e.getMessage());
    }
  }
}
