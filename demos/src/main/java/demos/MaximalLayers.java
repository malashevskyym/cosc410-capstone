package demos;

import edu.wofford.woclo.*;
import java.util.*;
import java.util.ArrayList;

public class MaximalLayers {
  List<int[]> points = new ArrayList<int[]>(15);
  String layers = "";

  public static void main(String... args) {

    LineParser parser = new LineParser("java MaximalLayers [-h] [--sortedX] [--sortedY] points",
        "Sort the points into layers.");
    parser.addRequiredArgument("points", LineParser.Datatype.STRING, "the data points");
    parser.addOptionalArgument("sortedX", LineParser.Datatype.BOOLEAN, "sort layers by x coordinate");
    parser.addOptionalArgument("sortedY", LineParser.Datatype.BOOLEAN, "sort layers by y coordinate");

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
      MaximalLayers maximalLayers = new MaximalLayers(parser.getArgument("points"), parser.getArgument("sortedX"),
          parser.getArgument("sortedY"));

      maximalLayers.findLayers();

      System.out.println(maximalLayers.layers);
    } catch (IllegalArgumentException e) {
      System.out.println("MaximalLayers error: " + e.getMessage());
    }
  }

  public MaximalLayers(String inputPoints, boolean sortX, boolean sortY) {
    String[] pointsArray = inputPoints.split(",");

    if (pointsArray.length % 2 != 0) {
      throw new IllegalArgumentException(pointsArray[pointsArray.length - 1] + " is an unpaired x coordinate");
    }

    for (int i = 0; i < pointsArray.length; i += 2) {
      try {
        points.add(new int[] { Integer.parseInt(pointsArray[i]), Integer.parseInt(pointsArray[i + 1]) });
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("the value " + pointsArray[i] + " is not of type integer");
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

  private List<int[]> findDominates(int[] maxNorth, int[] maxEast) {
    List<int[]> dominates = new ArrayList<int[]>();

    for (int i = 0; i < points.size(); i++) {

    }

    return dominates;
  }

  public String findLayers() {
    int currentLayer = 1;
    List<int[]> layer = new ArrayList<int[]>();
    StringBuffer buffer = new StringBuffer();
    int k = 0;
    while (!points.isEmpty()) {
      int maxNorth = -1;
      int northIndex = 0;
      int maxEast = -1;
      int eastIndex = 0;

      for (int i = 0; i < points.size(); i++) {
        if (points.get(i)[0] > maxEast) {
          maxEast = points.get(i)[0];
          eastIndex = i;
        }
        if (points.get(i)[1] > maxNorth) {
          maxNorth = points.get(i)[1];
          northIndex = i;
        }
      }

      int[] maxNorthPoint = points.get(k);
      int[] maxEastPoint = points.get(k);

      for (int i = 0; i < points.size(); i++) {
        if (points.get(i)[0] == maxEast && !layer.contains(points.get(i))) {
          layer.add(points.get(i));
        }
        if (points.get(i)[1] == maxNorth && !layer.contains(points.get(i))) {
          layer.add(points.get(i));
        }
      }
      points.removeAll(layer);

      buffer.append(Integer.valueOf(currentLayer) + ":");
      for (int i = 0; i < layer.size(); i++) {
        buffer.append("(" + Integer.valueOf(layer.get(i)[0]) + "," + Integer.valueOf(layer.get(i)[1]) + ")");
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
