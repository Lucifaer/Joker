package com.lucifaer.jokerframework.core.exception;

public class SessionContextNameAlreadyUsed extends JokerException {
    public SessionContextNameAlreadyUsed(String contextName) {
        this.message = contextName + " is already in use. Please change another name.";
    }
}
