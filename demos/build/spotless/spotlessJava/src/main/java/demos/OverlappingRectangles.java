package demos;

import edu.wofford.woclo.*;

// Total Area = (Area of 1st rectangle +
//              Area of 2nd rectangle) -
//              Area of Intersecting part
// need to heck if there is indeed an overlap.

public class OverlappingRectangles {

  public static void main(String... args) {
    // LineParser arguments = new LineParser(8, "Integer", args);
    // Integer[] coordinates = arguments.getArgs();

    Integer[] test = {-4, -2, 3, 2, -1, -3, 5, 3};
    OverlappingRectangles Obj = new OverlappingRectangles(test);
  }

  public OverlappingRectangles(Integer[] coord) {
    // First rectangle
    int x1 = coord[0];
    int y1 = coord[1];
    int x2 = coord[2];
    int y2 = coord[3];
    // Second Rectangle
    int x3 = coord[4];
    int y3 = coord[5];
    int x4 = coord[6];
    int y4 = coord[7];

    int totalArea = 0;
    int overlapArea = 0;

    overlapArea = getOverlap(x1, y1, x2, y2, x3, y3, x4, y4);
    totalArea = getArea(x1, y1, x2, y2) + getArea(x3, y3, x4, y4) - overlapArea;
    System.out.println(overlapArea + " " + totalArea);
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
