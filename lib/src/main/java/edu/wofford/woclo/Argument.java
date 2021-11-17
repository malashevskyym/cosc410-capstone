package edu.wofford.woclo;

import edu.wofford.woclo.LineParser.Datatype;

public class Argument {

  Datatype type;
  String value = "";
  String help = "";
  String shortName = "";

  public Argument(Datatype type) {
    this.type = type;
  }

  public Argument(Datatype type, String help) {
    this.type = type;
    this.help = help;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
