package demos;

import edu.wofford.woclo.*;

public class MaximalLayers {

  public static void main(String... args) {
    LineParser parser =
        new LineParser(
            "java MaximalLayers [-h] [--sortedX] [--sortedY] points",
            "Sort the points into layers.");
    parser.addRequiredArgument("points", LineParser.Datatype.STRING, "the first string");
    parser.addRequiredArgument("sortedX", LineParser.Datatype.BOOLEAN, "sort layers by x coordinate");
    parser.addRequiredArgument("sortedY", LineParser.Datatype.BOOLEAN, "sort layers by y coordinate");

    try {
      parser.parse(args);
      MaximalLayers maximalLayers =
          new MaximalLayers(parser.getArgument("points"));

      //System.out.println(equivalentStrings.message);
    } catch (Exception e) {
      System.out.println("MaximalLayers error: " + e.getMessage());
    }
  }

  public MaximalLayers() {
    
  }
}
