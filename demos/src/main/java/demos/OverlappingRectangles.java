package demos;

import javax.sound.sampled.Line;

import edu.wofford.woclo.*;

// Total Area = (Area of 1st rectangle + 
//              Area of 2nd rectangle) - 
//              Area of Intersecting part
// need to heck if there is indeed an overlap.

public static class Point {
        int x, y;
 
        public Point(int x, int y) {
          this.x = x;
          this.y = y;
        }
};

public class OverlappingRectangles {

  public static void main(String... args) {
    // LineParser arguments = new LineParser(8, "Integer", args);
    // Integer[] coordinates = arguments.getArgs();

    Integer[] test = { 2, 2, 4, 4, 3, 3, 6, 5 };
    OverlappingRectangles Obj = new OverlappingRectangles(test);

  }

  public OverlappingRectangles(Integer[] coord) {
    int coord[] = new int[] { args }; 
    int totalArea = 0;
    int overlapArea = 0;

    // bottom left point of the overlapping area.
    // random parameter subs right now
	  int bl_x = Math.max(x1, y1);
	  int bl_y = Math.max(x3, y3);

	  // top right point of the overlapping area.
	  int tr_x = Math.min(x2, y2);
	  int tr_y = Math.min(x4, y4);

    // if overlapping need something like "if(right > left && top > bottom)" then
    // OverlapArea should be the addition of area of the two rectangles minus the overlapping area.


	
	  totalArea = (tr_x - bl_x) * (tr_y - bl_y);

    System.out.println(totalArea + " " + overlapArea);
  }
}
