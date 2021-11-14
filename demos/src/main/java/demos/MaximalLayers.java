package demos;

import edu.wofford.woclo.*;
import java.util.*;

public class MaximalLayers {
  Vector<int[]> points = new Vector<int[]>(15);
  String layers = "";
  String error = "";

  public static void main(String... args) {
    LineParser parser =
        new LineParser(
            "java MaximalLayers [-h] [--sortedX] [--sortedY] points",
            "Sort the points into layers.");
    parser.addRequiredArgument("points", LineParser.Datatype.STRING, "the first string");
    parser.addOptionalArgument(
        "sortedX", LineParser.Datatype.BOOLEAN, "sort layers by x coordinate");
    parser.addOptionalArgument(
        "sortedY", LineParser.Datatype.BOOLEAN, "sort layers by y coordinate");

    try {
      parser.parse(args);
      MaximalLayers maximalLayers = new MaximalLayers(parser.getArgument("points"));
      if (parser.getArgument("sortedY")) {}

      if (parser.getArgument("sortedX")) {}

      maximalLayers.findLayers();

      System.out.println(maximalLayers.layers);
    } catch (Exception e) {
      System.out.println("MaximalLayers error: " + e.getMessage());
    }
  }

  public MaximalLayers(String points) {
    String[] pointsArray = points.split(",");
    /*
    if (pointsArray.length % 2 != 0) {
      throw new IllegalArgumentException(pointsArray[pointsArray.length - 1] + " is an unpaired x coordinate");
    }
    */
    for (int i = 0; i < pointsArray.length; i += 2) {
      this.points.addElement(
          new int[] {Integer.valueOf(pointsArray[i]), Integer.parseInt(pointsArray[i + 1])});
    }

    layers = findLayers();
  }

  public String findLayers() {
    String s = "";

    return s;
  }
}
