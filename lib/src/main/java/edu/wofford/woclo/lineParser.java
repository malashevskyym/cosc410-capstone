package edu.wofford.woclo;

import java.util.*;

public class lineParser {
    static String[] args = new String[] {};
    Hashtable<String, String> mainArgs = new Hashtable<String, String>();

    public lineParser(String[] args) {
        this.args = args;
    }

    public static boolean detectHelp() {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--help") || args[i].equals("-hi")) {
                return true;
            }
        }
        return false;
    }
}
