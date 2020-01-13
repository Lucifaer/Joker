package com.lucifaer.jokerframework.data;

import com.lucifaer.jokerframework.modules.Exploit;
import com.lucifaer.jokerframework.plugins.Server;

import java.util.HashMap;
import java.util.Map;

public class JokerContext {
    private Map<String, Exploit> existExploitMap = new HashMap<>();
    private Map<String, Server> existServerMap = new HashMap<>();
    private Map<String, ShellContext> shellContextMap = new HashMap<>();

    private String currentShell = null;

    public void shellRegister(ShellContext shellContext) {
        this.shellContextMap.put(shellContext.contextName, shellContext);
        this.currentShell = shellContext.contextName;
    }

    public Map<String, Exploit> getExistExploitMap() {
        return existExploitMap;
    }

    public void setExistExploitMap(Map<String, Exploit> existExploitMap) {
        this.existExploitMap = existExploitMap;
    }

    public Map<String, Server> getExistServerMap() {
        return existServerMap;
    }

    public void setExistServerMap(Map<String, Server> existServerMap) {
        this.existServerMap = existServerMap;
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
