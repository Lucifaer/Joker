package com.lucifaer.jokerframework.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ShellTestData {
    public static String defaultAttributedString = "Joker$ ";
    public static String currentAttributedString = "";
    public static String defaultCommandNode = "root";
    public static String currentCommandNode = "";
    public static Stack<String> preCommandNode = new Stack<>();
    public static Map<String, String> params = new HashMap<>();
}
