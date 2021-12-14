package demos;

import edu.wofford.woclo.*;

public class TilingAssistant {
  Float lengthRoom = 0f;
  Float widthRoom = 0f;
  Float lr;
  Float wr;
  Float lengthTile = 6.0f;
  Float widthTile = 6.0f;
  Float groutGap = 0.5f;
  String system = "in"; // can be inches or centimeters if "metric" is specified
  boolean fullOnly = false;
  String result;

  public TilingAssistant() {}

  public String getResult() {
    return result;
  }

  public void setFullOnly(boolean t) {
    if (t) {
      fullOnly = true;
    }
  }

  public void setLenRoom(Float lengthRoom) {
    if (lengthRoom <= 0) {
      throw new IllegalArgumentException("length must be positive");
    } else {
      this.lengthRoom = lengthRoom;
    }
  }

  public void setWidRoom(Float widthRoom) {
    if (widthRoom <= 0) {
      throw new IllegalArgumentException("width must be positive");
    } else {
      this.widthRoom = widthRoom;
    }
  }

  public void setSizeTile(Float tileSize) {
    if (tileSize <= 0) {
      throw new IllegalArgumentException("tilesize must be positive");
    } else {
      // always square
      lengthTile = tileSize;
      widthTile = tileSize;
    }
  }

  public void setGroutGap(Float groutGap) {
    if (groutGap <= 0) {
      throw new IllegalArgumentException("groutgap must be positive");
    } else {
      this.groutGap = groutGap;
    }
  }

  public void changeToMetric(boolean t) {
    if (t) {
      system = "cm";
    }
  }

  private Float checkWidthFullFit() {
    double wr = (double) widthRoom;
    double wt = (double) widthTile;
    Float count = 0f;
    while (wr > (wt + groutGap)) {
      if ((wr - wt) > groutGap) {
        wr = wr - (wt + groutGap);
        count += 1.0f;
      }
      System.out.println("" + wr + " " + count);
    }
    if (wr == wt) {
      wr = wr - wt;
      count += 1.0f;
    }
    this.wr = (float) wr;
    return count;
  }

  private Float checkLengthFullFit() {
    double lr = (double) lengthRoom;
    double lt = (double) lengthTile;
    Float count = 0f;
    while (lr > (lt + groutGap)) {
      if ((lr - lt) > groutGap) {
        lr = lr - (lt + groutGap);
        count += 1.0f;
      }
    }
    if (lr == lt) {
      lr = lr - lt;
      count += 1.0f;
    }
    this.lr = (float) lr;
    return count;
  }

  private Float checkLeftRighPartial() {
    Float widthLeft = wr - groutGap;
    Float reducedWid = widthLeft / 2;
    return reducedWid;
  }

  private Float checkTopBottomPartial() {
    Float widthLeft = lr - groutGap;
    Float reducedlen = widthLeft / 2;
    return reducedlen;
  }

  public String checkFit() {
    Float totalFullTiles = checkLengthFullFit() * checkWidthFullFit();
    Float totalTopBottom = 0f;
    Float totalLeftRight = 0f;
    String build = "";
    if (lr != 0) {
      totalTopBottom = checkWidthFullFit() * 2;
    }
    if (wr != 0) {
      totalLeftRight = checkLengthFullFit() * 2f;
    }
    if (fullOnly == true || checkLeftRighPartial() < 0 && checkTopBottomPartial() < 0) {
      build =
          ""
              + Math.round(totalFullTiles)
              + ":("
              + lengthTile
              + " x "
              + widthTile
              + " "
              + system
              + ")";
    } else if (lr == 0) {
      build =
          ""
              + Math.round(totalFullTiles)
              + ":("
              + lengthTile
              + " x "
              + widthTile
              + " "
              + system
              + ")"
              + " "
              + Math.round(totalLeftRight)
              + ":("
              + lengthTile
              + " x "
              + checkLeftRighPartial()
              + " "
              + system
              + ")";
    } else if (wr == 0) {
      build =
          ""
              + Math.round(totalFullTiles)
              + ":("
              + lengthTile
              + " x "
              + widthTile
              + " "
              + system
              + ")"
              + " "
              + Math.round(totalTopBottom)
              + ":("
              + checkTopBottomPartial()
              + " x "
              + widthTile
              + " "
              + system
              + ")";
    } else {
      build =
          ""
              + Math.round(totalFullTiles)
              + ":("
              + lengthTile
              + " x "
              + widthTile
              + " "
              + system
              + ")"
              + " "
              + Math.round(totalTopBottom)
              + ":("
              + checkTopBottomPartial()
              + " x "
              + widthTile
              + " "
              + system
              + ")"
              + " "
              + Math.round(totalLeftRight)
              + ":("
              + lengthTile
              + " x "
              + checkLeftRighPartial()
              + " "
              + system
              + ") "
              + 4
              + ":("
              + checkTopBottomPartial()
              + " x "
              + checkLeftRighPartial()
              + " "
              + system
              + ")";
    }
    result = build;
    return build;
  }

  public static void main(String... args) {
    LineParser parser =
        new LineParser(
            "java TilingAssistant [-h] [-s TILESIZE] [-g GROUTGAP] [-m] [-f] length width",
            "Calculate the tiles required to tile a room. All units are inches.");
    parser.addRequiredArgument("length", LineParser.Datatype.FLOAT, "the length of the room");
    parser.addRequiredArgument("width", LineParser.Datatype.FLOAT, "the width of the room");
    parser.addOptionalArgument(
        "tilesize", LineParser.Datatype.FLOAT, "6.0", "the size of the square tile", "s");
    parser.addOptionalArgument(
        "groutgap", LineParser.Datatype.FLOAT, "0.5", "the width of the grout gap", "g");
    parser.addOptionalArgument(
        "metric", LineParser.Datatype.BOOLEAN, "false", "use centimeters instead of inches", "m");
    parser.addOptionalArgument(
        "fullonly", LineParser.Datatype.BOOLEAN, "false", "show only the full tiles required", "f");
    try {
      parser.parse(args);
      TilingAssistant tilingAssistant = new TilingAssistant();
      try {
        tilingAssistant.setLenRoom(parser.getArgument("length"));
        tilingAssistant.setWidRoom(parser.getArgument("width"));
        tilingAssistant.setSizeTile(parser.getArgument("tilesize"));
        tilingAssistant.setGroutGap(parser.getArgument("groutgap"));
        tilingAssistant.changeToMetric(parser.getArgument("metric"));
        tilingAssistant.setFullOnly(parser.getArgument("fullonly"));
        String result = tilingAssistant.checkFit();
        System.out.println(result);
      } catch (Exception e) {
        System.out.println("TilingAssistant error: " + e.getMessage());
      }
    } catch (Exception e) {
      System.out.println("TilingAssistant error: " + e.getMessage());
    }
  }
}
