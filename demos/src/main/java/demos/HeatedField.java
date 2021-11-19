package demos;

import edu.wofford.woclo.*;

public class HeatedField {
  float northTemp;
  float southTemp;
  float eastTemp;
  float westTemp;
  int x;
  int y;
  float initialInteriorTemp = 32;
  int minutes = 10;
  double[][] field = new double[10][10];
  double[][] fieldCopy = new double[10][10];

  public HeatedField() {}

  public void setNorthTemp(float northTemp) {
    this.northTemp = northTemp;
  }

  public void setSouthTemp(float southTemp) {
    this.southTemp = southTemp;
  }

  public void setEastTemp(float eastTemp) {
    this.eastTemp = eastTemp;
  }

  public void setWestTemp(float westTemp) {
    this.westTemp = westTemp;
  }

  public void setTarget(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void setMinutes(int minutes) {
    if (minutes <= 0) {
      throw new IllegalArgumentException("minutes must be positive");
    } else {
      this.minutes = minutes;
    }
  }

  public void setInitialTemp(float initialInteriorTemp) {
    this.initialInteriorTemp = initialInteriorTemp;
  }

  public void createField() {
    for (int i = 0; i < 10; i++) {
      for (int t = 0; t < 10; t++) {
        if (i == 0 && t == 0 || i == 0 && t == 9 || i == 9 && t == 0 || i == 9 && t == 9) {
          field[i][t] = 0;
        } else if (i == 0) {
          field[i][t] = northTemp;
        } else if (t == 0) {
          field[i][t] = westTemp;
        } else if (i == 9) {
          field[i][t] = southTemp;
        } else if (t == 9) {
          field[i][t] = eastTemp;
        } else {
          field[i][t] = initialInteriorTemp;
        }
      }
    }
    makeCopy();
  }

  private void makeCopy() {
    for (int i = 0; i < 10; i++) {
      for (int t = 0; t < 10; t++) {
        fieldCopy[i][t] = field[i][t];
      }
    }
  }

  private void setFieldEqualToCopy() {
    for (int i = 0; i < 10; i++) {
      for (int t = 0; t < 10; t++) {
        field[i][t] = fieldCopy[i][t];
      }
    }
  }

  public void calcFieldTemps() {
    for (int i = 1; i <= 8; i++) {
      for (int t = 1; t <= 8; t++) {
        fieldCopy[i][t] =
            (field[i - 1][t] + field[i][t - 1] + field[i + 1][t] + field[i][t + 1]) / 4;
      }
    }
    setFieldEqualToCopy();
  }

  public void calcField() {
    for (int i = 0; i < minutes; i++) {
      calcFieldTemps();
      // System.out.println(returnField());
    }
  }

  public String getTemp() {
    createField();
    calcField();
    String t =
        "cell ("
            + x
            + ","
            + " "
            + y
            + ") is "
            + (float) field[x][y]
            + " degrees Fahrenheit after "
            + minutes
            + " minutes";
    return t;
  }

  public static void main(String... args) {
    LineParser parser =
        new LineParser(
            "java HeatedField [-h] [-t TEMPERATURE] [-m MINUTES] north south east west x y",
            "Calculate the internal cell temperature.");
    parser.addRequiredArgument(
        "north", LineParser.Datatype.FLOAT, "the temperature of the north edge");
    parser.addRequiredArgument(
        "south", LineParser.Datatype.FLOAT, "the temperature of the south edge");
    parser.addRequiredArgument(
        "east", LineParser.Datatype.FLOAT, "the temperature of the east edge");
    parser.addRequiredArgument(
        "west", LineParser.Datatype.FLOAT, "the temperature of the west edge");
    parser.addRequiredArgument(
        "x",
        LineParser.Datatype.INTEGER,
        "the x coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}",
        new String[] {"1", "2", "3", "4", "5", "6", "7", "8"});
    parser.addRequiredArgument(
        "y",
        LineParser.Datatype.INTEGER,
        "the y coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}",
        new String[] {"1", "2", "3", "4", "5", "6", "7", "8"});
    parser.addOptionalArgument(
        "temperature",
        LineParser.Datatype.FLOAT,
        "32.0",
        "the initial temperature of internal cells",
        "t");
    parser.addOptionalArgument(
        "minutes",
        LineParser.Datatype.INTEGER,
        "10",
        "the number of minutes to apply heating",
        "m");

    try {
      parser.parse(args);
      HeatedField nF = new HeatedField();
      try {
        nF.setNorthTemp(parser.getArgument("north"));
        nF.setSouthTemp(parser.getArgument("south"));
        nF.setEastTemp(parser.getArgument("east"));
        nF.setWestTemp(parser.getArgument("west"));
        nF.setMinutes(parser.getArgument("minutes"));
        nF.setInitialTemp(parser.getArgument("temperature"));
        nF.setTarget(parser.getArgument("x"), parser.getArgument("y"));
        String result = nF.getTemp();
        System.out.println(result);
      } catch (Exception e) {
        System.out.println("HeatedField error: " + e.getMessage());
      }
    } catch (Exception e) {
      System.out.println("HeatedField error: " + e.getMessage());
    }
  }
}
