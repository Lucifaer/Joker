package com.lucifaer.jokerframework.core.exception;

import java.util.Map;

public class PortAlreadyUsed extends JokerException {
    public PortAlreadyUsed(Map<String, String> serverInfo) {
        this.message = serverInfo.get("serverName") + " has already use port " + serverInfo.get("serverPort");
    }
}
