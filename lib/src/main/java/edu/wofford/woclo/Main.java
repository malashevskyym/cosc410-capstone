package edu.wofford.woclo;

import java.util.Arrays;

public class Main {
  public static void main(String[] args) {

    LineParser test = new LineParser(4, args);
    System.out.println(Arrays.toString(test.getArgs()));
  }
}
