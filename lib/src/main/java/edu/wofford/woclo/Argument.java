package edu.wofford.woclo;

public class Argument {

  LineParser.Datatype type;
  String value = "";
  String help = "";
  String shortName = "";
  String[] discreteValues = new String[] {};

  public Argument() {}

  public Argument(LineParser.Datatype type) {
    this.type = type;
  }

  public Argument(LineParser.Datatype type, String help) {
    this.type = type;
    this.help = help;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  // {box, pyramid, ellipsoid}
  public String getDiscreteAsString() {
    String s = "{";
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < discreteValues.length; i++) {
      if (i + 1 == discreteValues.length) {
        buf.append(discreteValues[i]);
      } else {
        buf.append(discreteValues[i] + ", ");
      }
    }
    s = s + buf;
    s = s + "} ";
    return s;
  }

  // <default>
  // <value>4</value>
  // </default>

  public String toString() {
    String argumentString = "";
    if (type == LineParser.Datatype.STRING) {
      argumentString = argumentString + "<type>string</type>\n";
    } else if (type == LineParser.Datatype.FLOAT) {
      argumentString = argumentString + "<type>float</type>\n";
    } else if (type == LineParser.Datatype.INTEGER) {
      argumentString = argumentString + "<type>integer</type>\n";
    } else if (type == LineParser.Datatype.BOOLEAN) {
      argumentString = argumentString + "<type>boolean</type>\n";
    }

    if (!value.equals("")) {
      argumentString = argumentString + "<default>\n<value>" + value + "</value>\n</default>\n";
    }
    if (!help.equals("")) {
      argumentString = argumentString + "<description>" + help + "</description>\n";
    }
    if (!shortName.equals("")) {
      argumentString = argumentString + "<shortname>" + shortName + "</shortname>\n";
    }
    // <restrictions>
    // <restriction>box</restriction>
    // <restriction>pyramid</restriction>
    // <restriction>ellipsoid</restriction>
    // </restrictions>
    if (discreteValues.length != 0) {
      argumentString = argumentString + "<restrictions>\n";

      StringBuffer buff = new StringBuffer();

      for (int i = 0; i < discreteValues.length; i++) {
        buff.append("<restriction>" + discreteValues[i] + "</restriction>\n");
      }
      argumentString = argumentString + buff;

      argumentString = argumentString + "</restrictions>\n";
    }
    return argumentString;
  }
}
