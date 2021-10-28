package demos;

import javax.sound.sampled.Line;

import edu.wofford.woclo.*;

public class OverlappingRectangles {

  public static void main(String... args) {
    // LineParser arguments = new LineParser(8, "Integer", args);
    // Integer[] coordinates = arguments.getArgs();

    Integer[] test = { 2, 2, 4, 4, 3, 3, 6, 5 };
    OverlappingRectangles Obj = new OverlappingRectangles(test);

  }

  public OverlappingRectangles(Integer[] coord) {
    int totalArea = 0;
    int overlapArea = 0;

    System.out.println(totalArea + " " + overlapArea);
  }
}
