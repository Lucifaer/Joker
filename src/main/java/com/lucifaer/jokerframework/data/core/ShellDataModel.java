package com.lucifaer.jokerframework.data.core;

import com.lucifaer.jokerframework.data.DataModel;

import java.util.Map;

public class ShellDataModel extends DataModel {
    private Map<String, Object> shellContext;

    public ShellDataModel(Map<String, Object> shellContext) {
        this.shellContext = shellContext;
    }

    public Map<String, Object> getShellContext() {
        return shellContext;
    }

    public Object getShellContextValue(String key) {
        return this.shellContext.get(key);
    }

    public void setShellContext(Map<String, Object> shellContext) {
        this.shellContext = shellContext;
    }

    public void setShellContextValue(String key, Object value) {
        this.shellContext.replace(key, value);
    }
}
