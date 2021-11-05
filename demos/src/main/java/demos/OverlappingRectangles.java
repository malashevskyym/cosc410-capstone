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
    LineParser parse =
        new LineParser(
            "java OverlappingRectangles [-h] x1 y1 x2 y2 x3 y3 x4 y4\n\nDetermine the overlap and total area of two rectangles.");
    parse.addRequiredArgument("x1", Datatype.INTEGER, "lower-left x for rectangle 1");
    parse.addRequiredArgument("y1", Datatype.INTEGER, "lower-left y for rectangle 1");
    parse.addRequiredArgument("x2", Datatype.INTEGER, "upper-right x for rectangle 1");
    parse.addRequiredArgument("y2", Datatype.INTEGER, "upper-right y for rectangle 1");
    parse.addRequiredArgument("x3", Datatype.INTEGER, "lower-left x for rectangle 2");
    parse.addRequiredArgument("y3", Datatype.INTEGER, "lower-left y for rectangle 2");
    parse.addRequiredArgument("x4", Datatype.INTEGER, "upper-right x for rectangle 2");
    parse.addRequiredArgument("y4", Datatype.INTEGER, "upper-right y for rectangle 2");

    try {
      parse.parse(args);
      Integer[] values =
          new Integer[] {
            parse.getArgument("x1"),
            parse.getArgument("y1"),
            parse.getArgument("x2"),
            parse.getArgument("y2"),
            parse.getArgument("x3"),
            parse.getArgument("y3"),
            parse.getArgument("x4"),
            parse.getArgument("y4")
          };

      OverlappingRectangles Rectangles = new OverlappingRectangles(values);

      System.out.println(Rectangles.overlapArea + " " + Rectangles.totalArea);
    } catch (Exception e) {
      System.out.println("OverlappingRectangles error: " + e.getMessage());
    }
  }

  public OverlappingRectangles(Integer[] integers) {
    // First rectangle
    int x1 = integers[0];
    int y1 = integers[1];
    int x2 = integers[2];
    int y2 = integers[3];
    // Second Rectangle
    int x3 = integers[4];
    int y3 = integers[5];
    int x4 = integers[6];
    int y4 = integers[7];

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
