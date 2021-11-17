package demos;

import edu.wofford.woclo.*;
import java.util.*;
import java.util.ArrayList;

public class MaximalLayers {
  List<int[]> points = new ArrayList<int[]>(15);
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
      MaximalLayers maximalLayers =
          new MaximalLayers(
              parser.getArgument("points"),
              parser.getArgument("sortedX"),
              parser.getArgument("sortedY"));

      maximalLayers.findLayers();

      System.out.println(maximalLayers.layers);
    } catch (Exception e) {
      System.out.println("MaximalLayers error: " + e.getMessage());
    }
  }

  public MaximalLayers(String inputPoints, boolean sortX, boolean sortY) {
    String[] pointsArray = inputPoints.split(",");

    if (pointsArray.length % 2 != 0) {
      throw new IllegalArgumentException(
          pointsArray[pointsArray.length - 1] + " is an unpaired x coordinate");
    }

    for (int i = 0; i < pointsArray.length; i += 2) {
      try {
        points.add(
            new int[] {Integer.parseInt(pointsArray[i]), Integer.parseInt(pointsArray[i + 1])});
      } catch (Exception e) {
        throw new IllegalArgumentException(
            "the value " + pointsArray[i] + " is not of type integer");
      }
    }

    if (sortY) {
      for (int i = 0; i < points.size(); i++) {
        for (int j = 0; j < points.size() - i - 1; j++) {
          if (points.get(j)[1] > points.get(j + 1)[1]) {
            int[] tmp = points.get(j);
            points.set(j, points.get(j + 1));
            points.set(j + 1, tmp);
          }
        }
      }
    }

    if (sortX) {
      for (int i = 0; i < points.size(); i++) {
        for (int j = 0; j < points.size() - i - 1; j++) {
          if (points.get(j)[0] > points.get(j + 1)[0]) {
            int[] tmp = points.get(j);
            points.set(j, points.get(j + 1));
            points.set(j + 1, tmp);
          }
        }
      }
    }

    layers = findLayers();
  }

  public String findLayers() {
    String s = "";

    List<int[]> layer = new ArrayList<int[]>();
    int currentLayer = 1;
    while (!points.isEmpty()) {
      int maxNorth = -1;
      int maxEast = -1;

      for (int i = 0; i < points.size(); i++) {
        if (points.get(i)[0] > maxEast) {
          maxEast = points.get(i)[0];
        }
        if (points.get(i)[1] > maxNorth) {
          maxNorth = points.get(i)[1];
        }
      }

      for (int i = 0; i < points.size(); i++) {
        if (points.get(i)[0] == maxEast && !layer.contains(points.get(i))) {
          layer.add(points.get(i));
        }
        if (points.get(i)[1] == maxNorth && !layer.contains(points.get(i))) {
          layer.add(points.get(i));
        }
      }
      points.removeAll(layer);

      s += Integer.valueOf(currentLayer) + ":";
      for (int i = 0; i < layer.size(); i++) {
        s += "(" + Integer.valueOf(layer.get(i)[0]) + "," + Integer.valueOf(layer.get(i)[1]) + ")";
      }
      if (!points.isEmpty()) {
        s += " ";
      }

      currentLayer++;
      layer.clear();
    }

    return s;
  }
}
