package com.lucifaer.jokerframework.sdk.context;

import com.lucifaer.jokerframework.sdk.context.Context;

import java.util.Map;
import java.util.Stack;

/**
 * ShellContext为通用交互式命令行上下文，用于保存交互式命令行的参数
 * @author Lucifaer
 * @version 3.0
 */
public class ShellContext implements Context {
    private String contextName;
    private String contextType = "shell";
//  用于保存参数
    private Map<String, String> params;
//  用于保存命令节点
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
