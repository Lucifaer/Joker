package com.lucifaer.jokerframework.data.core;

import com.lucifaer.jokerframework.data.DataModel;

import java.util.Map;
import java.util.Stack;

public class ShellDataModel extends DataModel {
    private String defaultAttributedString;
    private String currentAttributedString;
    private String defaultCommandNode;
    private String currentCommandNode;
    private Stack<String> preCommandNode;
    private Map<String, String> params;

    public String getDefaultAttributedString() {
        return defaultAttributedString;
    }

    public void setDefaultAttributedString(String defaultAttributedString) {
        this.defaultAttributedString = defaultAttributedString;
    }

    public String getCurrentAttributedString() {
        return currentAttributedString;
    }

    public void setCurrentAttributedString(String currentAttributedString) {
        this.currentAttributedString = currentAttributedString;
    }

    public String getDefaultCommandNode() {
        return defaultCommandNode;
    }

    public void setDefaultCommandNode(String defaultCommandNode) {
        this.defaultCommandNode = defaultCommandNode;
    }

    public String getCurrentCommandNode() {
        return currentCommandNode;
    }

    public void setCurrentCommandNode(String currentCommandNode) {
        this.currentCommandNode = currentCommandNode;
    }

    public Stack<String> getPreCommandNode() {
        return preCommandNode;
    }

    public void setPreCommandNode(Stack<String> preCommandNode) {
        this.preCommandNode = preCommandNode;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
