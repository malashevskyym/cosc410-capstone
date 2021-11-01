package demos;

import edu.wofford.woclo.*;
import edu.wofford.woclo.LineParser.Datatype;

// Total Area = (Area of 1st rectangle +
//              Area of 2nd rectangle) -
//              Area of Intersecting part
// need to heck if there is indeed an overlap.

public class OverlappingRectangles {
  int totalArea = 0;
  int overlapArea = 0;

  public static void main(String... args) {
    // LineParser arguments = new LineParser(8, "Integer", args);
    // Integer[] coordinates = arguments.getArgs();

    LineParser arguments = new LineParser(8, args, Datatype.INTEGER);

    OverlappingRectangles Rectangles =
        new OverlappingRectangles(arguments.getArgumentsAsIntegers());
    System.out.println(Rectangles.overlapArea + " " + Rectangles.totalArea);
  }

  public OverlappingRectangles(int[] is) {
    // First rectangle
    int x1 = is[0];
    int y1 = is[1];
    int x2 = is[2];
    int y2 = is[3];
    // Second Rectangle
    int x3 = is[4];
    int y3 = is[5];
    int x4 = is[6];
    int y4 = is[7];

    overlapArea = getOverlap(x1, y1, x2, y2, x3, y3, x4, y4);
    totalArea = getArea(x1, y1, x2, y2) + getArea(x3, y3, x4, y4) - overlapArea;
  }

  // get rectangle area
  private Integer getArea(Integer x1, Integer y1, Integer x2, Integer y2) {
    int area = 0;

    int base = Math.abs(Math.max(x1, x2) - Math.min(x1, x2));
    int height = Math.abs(Math.max(y1, y2) - Math.min(y1, y2));

    area = base * height;

    return area;
  }

  // Get overlap area
  private Integer getOverlap(
      Integer x1,
      Integer y1,
      Integer x2,
      Integer y2,
      Integer x3,
      Integer y3,
      Integer x4,
      Integer y4) {
    int overlap = 0;

    int x_overlap = Math.max(0, Math.min(x2, x4) - Math.max(x1, x3));
    int y_overlap = Math.max(0, Math.min(y2, y4) - Math.max(y1, y3));
    overlap = x_overlap * y_overlap;

    return overlap;
  }
}
