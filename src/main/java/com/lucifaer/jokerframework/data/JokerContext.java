package com.lucifaer.jokerframework.data;

import java.util.HashMap;
import java.util.Map;

public class JokerContext {
    private Map<String, ShellContext> shellContextMap = new HashMap<>();

    private String currentShell = null;

    public void shellRegister(ShellContext shellContext) {
        this.shellContextMap.put(shellContext.contextName, shellContext);
        this.currentShell = shellContext.contextName;
    }

    public Map<String, ShellContext> getShellContextMap() {
        return shellContextMap;
    }

    public String getCurrentShell() {
        return currentShell;
    }

    public ShellContext getCurrentShellContext() {
        try {
            assert !this.shellContextMap.isEmpty();
            assert this.currentShell!=null;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return this.shellContextMap.get(this.currentShell);
    }
}
