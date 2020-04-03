package com.lucifaer.jokerframework.core.exception;

public class ServerContextNameNotFound extends JokerException {
    public ServerContextNameNotFound(String processId) {
        this.message = processId + " is not found in ServerContext.";
    }
}
