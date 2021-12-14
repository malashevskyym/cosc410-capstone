package demos;

import edu.wofford.woclo.*;

public class VolumeCalculator {

  double volume;
  double length;
  double width;
  double heigth;
  String shape = "box";
  int precision = 4;

  public VolumeCalculator(double length, double width, double heigth) {
    this.length = length;
    this.width = width;
    this.heigth = heigth;
  }

  public void setShape(String shape) {
    this.shape = shape;
  }

  public String getShape() {
    return shape;
  }

  public void setPrecision(int precision) {
    this.precision = precision;
  }

  public int getPrecision() {
    return precision;
  }

  public double getLength() {
    return length;
  }

  public double getHeight() {
    return heigth;
  }

  public double getWidth() {
    return width;
  }

  public double calcVolume() {
    if (shape.contentEquals("box")) {
      volume = length * width * heigth;
    } else if (shape.contentEquals("pyramid")) {
      volume = (length * width * heigth) / 3;
    } else if (shape.contentEquals("ellipsoid")) {
      volume = (4.0 / 3.0) * Math.PI * (length * width * heigth);
    }
    double scale = Math.pow(10, precision);
    volume = Math.round(volume * scale) / scale;
    return volume;
  }

  public static void main(String... args) {
    LineParser parser = new LineParser();
    parser.addArgsFromFile(
        "<?xml version=\"1.0\"?><arguments><positionalArgs><positional><type>float</type><description>the length of the volume</description><name>length</name></positional><positional><type>float</type><name>width</name><description>the width of the volume</description></positional><positional><description>the height of the volume</description><name>height</name><type>float</type></positional></positionalArgs><namedArgs><named><description>the type of volume</description><shortname>t</shortname><type>string</type><name>type</name><restrictions><restriction>box</restriction><restriction>pyramid</restriction><restriction>ellipsoid</restriction></restrictions><default><value>box</value></default></named><named><default><value>4</value></default><type>integer</type><description>the maximum number of decimal places for the volume</description><name>precision</name><shortname>p</shortname></named></namedArgs></arguments>");
    try {
      parser.parse(args);
    } catch (Exception e) {
      System.out.println(e);
    }
    VolumeCalculator t = new VolumeCalculator(2, 5, 3);
    t.setShape("ellipsoid");
    t.setPrecision(3);
    System.out.println(t.calcVolume());
  }
}
