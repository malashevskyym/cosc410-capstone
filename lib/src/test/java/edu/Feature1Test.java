package edu.wofford.woclo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class Feature1Test {

  @Test
  public void testF1NoArguments() {
    // Simulating passing arguments into main
    Feature1 temp = new Feature1();
    Main.main(new String[] {""});
    int ln;
    if (temp.mapPositionalArguments().isEmpty()) {
      ln = 0;
    } else {
      ln = temp.mapPositionalArguments().size();
    }
    assertEquals(0, ln);
  }

  @Test
  public void testF1IsNotEmpty() {
    // Simulating passing arguments into main
    Feature1 temp = new Feature1();
    Main.main(new String[] {""});
    int ln;
    if (temp.mapPositionalArguments().isEmpty()) {
      ln = 0;
    } else {
      ln = temp.mapPositionalArguments().size();
    }
    assertEquals(1, ln);
  }
  // Nothing, one, isString
}
