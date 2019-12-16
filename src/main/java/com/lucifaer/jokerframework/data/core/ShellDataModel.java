package com.lucifaer.jokerframework.data.core;

import com.lucifaer.jokerframework.data.DataModel;

import java.util.HashMap;
import java.util.Map;

public class ShellDataModel extends DataModel {
    private Map<String, Object> shellContext = new HashMap<>();

    public Map<String, Object> getShellContext() {
        return shellContext;
    }

    public void setShellContext(String key, Object value) {
        shellContext.replace(key, value);
    }

    public Object getShellContextValue(String key) {
        return shellContext.get(key);
    }

}
