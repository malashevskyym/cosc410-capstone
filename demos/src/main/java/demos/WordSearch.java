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
  ArrayList<String> coordinates = new ArrayList<String>();
  String coordinatesString = "";

  public WordSearch() {}

  public void setHeight(int height) {
    this.height = height;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public void setBoard(String bd) {
    char[] temp = bd.toCharArray();
    board = new char[height][width];
    int index = 0;
    for (int i = 0; i < height; i++) {
      for (int t = 0; t < width; t++) {
        board[i][t] = temp[index++];
      }
    }
  }

  private void setCoordinate(char a, int b, int c) {
    String temp = String.valueOf(a) + ":" + (b + 1) + "," + (c + 1);
    coordinates.add(temp);
  }

  private void findFirstLetter(int Iindex, int Tindex) {
    wordBuild = "";
    coordinates.clear();
    outerloop:
    for (int i = Iindex; i < height; i++) {
      if (i > Iindex) {
        Tindex = 0;
      }
      for (int t = Tindex; t < width; t++) {
        if (board[i][t] == word.charAt(0)) {
          wordBuild = wordBuild + board[i][t];
          row = i;
          col = t;
          setCoordinate(board[i][t], i, t);
          findNextLetter(1, i, t);
          break outerloop;
        }
      }
    }
    if (!wordBuild.contentEquals(word)) {
      coordinates.clear();
    }
  }

  public boolean lookRight(int index, int z, int y) {
    if (wordBuild.contentEquals(word)) {
    } else if (y < width - 1 && board[z][y + 1] == word.charAt(index)) {
      wordBuild = wordBuild + board[z][y + 1];
      setCoordinate(board[z][y + 1], z, y + 1);
      findNextLetter(index + 1, z, y + 1);
      return true;
    }
    return false;
  }

  public boolean lookLeft(int index, int z, int y) {
    if (wordBuild.contentEquals(word)) {
    } else if (y > 0 && y <= width - 1 && board[z][y - 1] == word.charAt(index)) {
      wordBuild = wordBuild + board[z][y - 1];
      setCoordinate(board[z][y - 1], z, y - 1);
      findNextLetter(index + 1, z, y - 1);
    }
    return false;
  }

  public boolean lookDown(int index, int z, int y) {
    if (wordBuild.contentEquals(word)) {
    } else if (z < height - 1 && board[z + 1][y] == word.charAt(index)) {
      wordBuild = wordBuild + board[z + 1][y];
      setCoordinate(board[z + 1][y], z + 1, y);
      findNextLetter(index + 1, z + 1, y);
    } else if (z < height - 1 && board[z + 1][y] == word.charAt(index)) {
      wordBuild = wordBuild + board[z + 1][y];
      setCoordinate(board[z][y + 1], z + 1, y);
      findNextLetter(index + 1, z + 1, y);
    }
    return false;
  }

  public boolean lookUp(int index, int z, int y) {
    if (wordBuild.contentEquals(word)) {
    } else if (z > 0 && z <= height - 1 && board[z - 1][y] == word.charAt(index)) {
      wordBuild = wordBuild + board[z - 1][y];
      setCoordinate(board[z - 1][y], z - 1, y);
      findNextLetter(index + 1, z - 1, y);
    }
    return false;
  }

  private void findNextLetter(int index, int z, int y) {
    if (wordBuild.contentEquals(word)) {
      System.out.println(wordBuild);
    } else {
      if (lookUp(index, z, y)) {
      } else if (lookDown(index, z, y)) {
      } else if (lookRight(index, z, y)) {
      } else if (lookLeft(index, z, y)) {
      } else if (!wordBuild.contentEquals(word)) {
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
    String listString = String.join(" ", coordinates);
    if (coordinates.isEmpty()) {
      coordinatesString = word + " not found";
    } else {
      coordinatesString = listString;
    }
    return coordinatesString;
  }

  public static void main(String[] args) {
    LineParser testLine = new LineParser("Find a target word in a grid.");
    testLine.addRequiredArgument("grid", LineParser.Datatype.STRING, "the grid to search");
    testLine.addRequiredArgument("target", LineParser.Datatype.STRING, "the target word");
    testLine.addOptionalArgument("width", LineParser.Datatype.INTEGER, "5");
    testLine.addOptionalArgument("height", LineParser.Datatype.INTEGER, "5");
    try {
      testLine.parse(args);
      WordSearch test = new WordSearch();
      test.setHeight(testLine.getArgument("height"));
      test.setWidth(testLine.getArgument("width"));
      test.setBoard(testLine.getArgument("grid"));
      test.setWord(testLine.getArgument("target"));
      test.findWord();
      String wordResult = test.getFoundCoordinate();
      System.out.println(wordResult);
    } catch (Exception e) {
      System.out.println("WordSearch error: " + e.getMessage());
    }
  }
}
