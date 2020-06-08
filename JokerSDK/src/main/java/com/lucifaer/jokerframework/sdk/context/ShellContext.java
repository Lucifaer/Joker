package com.lucifaer.jokerframework.sdk.context;

import java.util.Map;
import java.util.Stack;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/3
 */
public class ShellContext implements Context {
    private String contextName;
    private String contextType = "shell";

    private Map<String, String> params;

    public Stack<String> commandNode = new Stack<>();

    public void setContextType(String contextType) {
        this.contextType = contextType;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Stack<String> getCommandNode() {
        return commandNode;
    }

    public void setCommandNode(Stack<String> commandNode) {
        this.commandNode = commandNode;
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
}
