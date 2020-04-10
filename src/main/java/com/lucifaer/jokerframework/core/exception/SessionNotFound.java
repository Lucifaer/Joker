package com.lucifaer.jokerframework.core.exception;

public class SessionNotFound extends JokerException {
    public SessionNotFound(String sessionId) {
        this.message = sessionId + " is not found in sessionMap.";
    }
}
