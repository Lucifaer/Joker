package com.lucifaer.jokerframework.joker.core.error.common;

import com.lucifaer.jokerframework.joker.core.error.JokerException;

/**
 * 处理ContextType不符合期望类型的异常
 * @author Lucifaer
 * @version 3.0
 */
public class ContextTypeError extends JokerException {
    public ContextTypeError(String expectedType, String contextType) {
        super("Expected type is " + expectedType + " , not " + contextType);
    }
}
