package com.lucifaer.jokerframework.core.exception;

public class ServerNotFound extends JokerException {
    public ServerNotFound(String serverName) {
        this.message = "No server name " + serverName;
    }
}
