package demos;

import edu.wofford.woclo.*;

public class XMLReadWriteDemo {

  public static void main(String... args) {
    LineParser parser = new LineParser();
    parser.addArgsFromString(args[0]);
    parser.createXmlFile(args[1]);
  }
}
