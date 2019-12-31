package com.lucifaer.jokerframework.data;

import java.util.Map;
import java.util.Stack;

public class ShellContext {
    private int id = -1;

    public String contextName;

    public Stack<String> commandNode = new Stack<>();

    private Map<String, String> params;

    public ShellContext() {
        this.id++;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
