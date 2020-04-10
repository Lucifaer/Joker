package com.lucifaer.jokerframework.core.exception;

public class ContextTypeError extends JokerException {
    public ContextTypeError(String expectedType, String contextType) {
        this.message = "Expected type is " + expectedType + ", not " + contextType;
    }
}
