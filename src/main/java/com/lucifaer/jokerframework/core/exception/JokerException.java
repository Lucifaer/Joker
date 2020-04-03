package com.lucifaer.jokerframework.core.exception;

public abstract class JokerException extends Throwable {
    String message;
    public String toString() {
        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
