package demos;

import edu.wofford.woclo.*;
import java.util.*;
import java.util.ArrayList;

public class MaximalLayers {
  List<int[]> points = new ArrayList<int[]>(15);
  String layers = "";

  public static void main(String... args) {

    LineParser parser =
        new LineParser(
            "java MaximalLayers [-h] [--sortedX] [--sortedY] points",
            "Sort the points into layers.");
    parser.addRequiredArgument("points", LineParser.Datatype.STRING, "the data points");
    parser.addOptionalArgument(
        "sortedX", LineParser.Datatype.BOOLEAN, "false", "sort layers by x coordinate");
    parser.addOptionalArgument(
        "sortedY", LineParser.Datatype.BOOLEAN, "false", "sort layers by y coordinate");

    try {
      parser.parse(args);
      String value = parser.getArgument("points");
      String[] values = value.split(",");
      for (int i = 0; i < values.length; i++) {
        try {
          Integer.parseInt(values[i]);
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("the value " + values[i] + " is not of type integer");
        }
      }
      MaximalLayers maximalLayers =
          new MaximalLayers(
              parser.getArgument("points"),
              parser.getArgument("sortedX"),
              parser.getArgument("sortedY"));

      maximalLayers.findLayers();

      System.out.println(maximalLayers.layers);
    } catch (IllegalArgumentException e) {
      if (!parser.detectHelp(args)) {
        System.out.println("MaximalLayers error: " + e.getMessage());
      }
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
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException(
            "the value " + pointsArray[i] + " is not of type integer");
      }
    }

    if (sortY) {
      sortPointsByY();
    }
    if (sortX) {
      sortPointsByX();
    }

    layers = findLayers();
  }

  private void sortPointsByX() {
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

  private void sortPointsByY() {
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

  private boolean checkIfPartiallyDominates(int[] pointA, int[] pointB) {
    if (pointA[0] >= pointB[0] || pointA[1] >= pointB[1]) {
      return true;
    }
    return false;
  }

  private List<int[]> findLayer() {
    List<int[]> layer = new ArrayList<int[]>();

    boolean flag;
    for (int i = 0; i < points.size(); i++) {
      flag = true;
      for (int j = 0; j < points.size(); j++) {
        if (i == j) {
          continue;
        } else {
          if (!checkIfPartiallyDominates(points.get(i), points.get(j))) {
            flag = false;
          }
        }
      }
      if (flag == true) {
        layer.add(points.get(i));
      }
    }

    return layer;
  }

  public String findLayers() {
    int currentLayer = 1;
    List<int[]> layer = new ArrayList<int[]>();
    StringBuffer buffer = new StringBuffer();

    while (!points.isEmpty()) {
      layer.addAll(findLayer());
      points.removeAll(layer);

      buffer.append(Integer.valueOf(currentLayer) + ":");
      for (int i = 0; i < layer.size(); i++) {
        buffer.append(
            "(" + Integer.valueOf(layer.get(i)[0]) + "," + Integer.valueOf(layer.get(i)[1]) + ")");
      }
      if (!points.isEmpty()) {
        buffer.append(" ");
      }

      currentLayer++;
      layer.clear();
    }

    String s = buffer.toString();
    return s;
  }
}
