package com.lucifaer.jokerframework.joker.core.error;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/3
 */
public class JokerException extends Throwable {
    protected String message;

    public JokerException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "[Error] " + message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
