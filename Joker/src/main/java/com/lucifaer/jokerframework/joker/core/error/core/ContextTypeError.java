package com.lucifaer.jokerframework.joker.core.error.core;

import com.lucifaer.jokerframework.joker.core.error.JokerException;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/3
 */
public class ContextTypeError extends JokerException {
    public ContextTypeError(String expectedType, String contextType) {
        super("Expected type is " + expectedType + " , not " + contextType + ".");
    }
}
