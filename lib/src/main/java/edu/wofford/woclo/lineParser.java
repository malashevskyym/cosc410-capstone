package edu.wofford.woclo;

import java.util.*;

public class LineParser {
  Map<String, Argument> arguments = new HashMap<String, Argument>();
  List<String> argsPostion = new ArrayList<String>();
  String useInfo;

  public enum Datatype {
    STRING("STRING"),
    INTEGER("INT"),
    FLOAT("FLOAT");

    public final String label;

    private Datatype(String label) {
      this.label = label;
    }
  };

  public LineParser() {};

  public LineParser(String useInfo) {
    this.useInfo = useInfo;
  }

  public void addRequiredArgument(String name, Datatype type) {
    arguments.put(name, new Argument(type));
    argsPostion.add(name);
  }

  public void addRequiredArgument(String name, Datatype type, String help) {
    arguments.put(name, new Argument(type, help));
    argsPostion.add(name);
  }

  public void addOptionalArgument(String name, Datatype type) {
    arguments.put(name, new Argument(type));
  }

  public void addOptionalArgument(String name, Datatype type, String help) {
    arguments.put(name, new Argument(type, help));
  }

  public String[][] getArg() {
    String[][] arg = new String[arguments.keySet().size()][4];
    int i = 0;
    for (Map.Entry<String, Argument> entry : arguments.entrySet()) {
      arg[i][0] = entry.getKey();
      arg[i][1] = arguments.get(entry.getKey()).type.label;
      arg[i][2] = arguments.get(entry.getKey()).value;
      arg[i][3] = arguments.get(entry.getKey()).help;
      i++;
    }
    return arg;
  }

  public void parse(String[] args) {
    List<String> optionals = new ArrayList<String>();
    List<String> required = new ArrayList<String>();
    String helpMessage = constructHelpMessage();
    if (detectHelp(args)) {
      System.out.println(helpMessage);
    } else {
      for (int i = 0; i < args.length; i++) {
        required.add(args[i]);
      }

      for (int i = 0; i < required.size(); i++) {

        if (required.get(i).substring(0, 1).equals("-")) {

          if (required.get(i).substring(1, 2).equals("-")) {

            optionals.add(args[i]);
            optionals.add(args[i + 1]);
            required.remove(i);
            required.remove(i);
          }
        }
      }

      ;

      for (int i = 0; i < required.size(); i++) {
        arguments.get(argsPostion.get(i)).setValue(required.get(i));
      }
      for (int i = 0; i < optionals.size(); i++) {

        for (Map.Entry<String, Argument> entry : arguments.entrySet()) {
          System.out.println(entry.getKey());
          if (entry.getKey().length() > 2) {
            if (entry.getKey().equals(optionals.get(i).substring(2))) {
              System.out.println("Entered conditional");
              entry.getValue().setValue(optionals.get(i + 1));
            }
          }
        }
      }
    }
  }

  private String constructHelpMessage() {
    String helpMessage = "";

    helpMessage += "Usage:";
    helpMessage += useInfo + "\npositional arguments:";
    StringBuffer buf = new StringBuffer();
    for (Map.Entry<String, Argument> entry : arguments.entrySet()) {

      if (entry.getValue().type == Datatype.INTEGER) {
        buf.append(entry.getKey() + "          " + "(integer)" + "     " + entry.getValue().help);
      } else if (entry.getValue().type == Datatype.FLOAT) {
        buf.append(entry.getKey() + "          " + "(float)" + "     " + entry.getValue().help);
      } else if (entry.getValue().type == Datatype.STRING) {
        buf.append(entry.getKey() + "          " + "(string)" + "     " + entry.getValue().help);
      }
    }

    helpMessage += "named arguments:\n -h, --help  show this help message and exit";
    return helpMessage;
  }

  private boolean detectHelp(String[] arguments) {
    for (int i = 0; i < arguments.length; i++) {
      if (arguments[i].equals("-h") || arguments[i].equals("--help")) {
        return true;
      }
    }
    return false;
  }
}
