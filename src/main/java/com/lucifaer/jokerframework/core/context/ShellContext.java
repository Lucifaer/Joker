package com.lucifaer.jokerframework.core.context;

import java.util.Map;
import java.util.Stack;

public class ShellContext implements Context {
    private int id = 1;
    private String contextName;
    private String contextType = "shell";

    private Map<String, String> params;
    public Stack<String> commandNode = new Stack<>();

    public ShellContext() {
        this.id++;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public String getContextName() {
        return contextName;
    }

    @Override
    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    @Override
    public String getContextType() {
        return contextType;
    }

    public void setContextType(String contextType) {
        this.contextType = contextType;
    }
}
