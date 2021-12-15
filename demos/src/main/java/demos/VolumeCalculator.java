package demos;

import edu.wofford.woclo.*;

public class VolumeCalculator {

  Double volume;
  Double length;
  Double width;
  Double heigth;
  String shape = "box";
  int precision = 4;

  public VolumeCalculator(Float length, Float width, Float heigth) {
    this.length = (double) length;
    this.width = (double) width;
    this.heigth = (double) heigth;
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
    volume = 0.0000000;
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
    LineParser parser = new LineParser("java VolumeCalculator", "Calculate the volume.");
    String xml = args[0];
    String[] Argument = new String[args.length - 1];
    for (int i = 1; i < args.length; i++) {
      Argument[i - 1] = args[i];
    }
    try {
      parser.addArgsFromString(xml);
      parser.parse(Argument);
      Float width = parser.getArgument("width");
      Float length = parser.getArgument("length");
      Float height = parser.getArgument("height");
      VolumeCalculator t = new VolumeCalculator(length, width, height);
      String type = parser.getArgument("type");
      Integer precision = parser.getArgument("precision");
      System.out.println("shape: " + type);
      System.out.println("precision: " + precision);
      t.setShape(type);
      t.setPrecision(precision);
      System.out.println(t.calcVolume());
    } catch (HelpException e) {
      System.out.println(e.getMessage());
    } catch (InvalidXMLException e) {
      System.out.println("VolumeCalculator error: " + e.getMessage());
    } catch (Exception e) {
      System.out.println("VolumeCalculator error: " + e.getMessage());
    }
  }
}
