package com.lucifaer.jokerframework.core.exception;

public class SessionContextNameNotFound extends JokerException {
    public SessionContextNameNotFound(String sessionId) {
        this.message = sessionId + " is not found in SessionContext.";
    }
}
