package com.lucifaer.jokerframework.data.JokerShellDataModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public abstract class BaseJokerShellDataModel {
//  默认前缀
    public static String defaultAttributedString = "Joker$ ";
//  当前前缀
    public static String currentAttributedString = "";
//  默认的命令节点
    public static String defaultCommandNode = "root";
//  当前命令节点
    public static String currentCommandNode = "";
//  上一个命令节点
    public static Stack<String> preCommandNode = new Stack<>();
//  用于存储参数
    public static Map<String, Object> params = new HashMap<>();
}
