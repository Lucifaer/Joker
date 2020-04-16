package com.lucifaer.jokerframework.joker.core.error.server;

import com.lucifaer.jokerframework.joker.core.error.JokerException;

import java.util.Map;

/**
 * 处理端口已经占用的异常
 * @author Lucifaer
 * @version 3.0
 */
public class PortAlreadyUsed extends JokerException {
    public PortAlreadyUsed(Map<String, String> serverInfo) {
        super(serverInfo.get("serverName") + " has already use port " + serverInfo.get("serverPort"));
    }
}
