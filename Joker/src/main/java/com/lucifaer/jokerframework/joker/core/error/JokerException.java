package com.lucifaer.jokerframework.joker.core.error;

/**
 * JokerException负责提供Joker自定义异常处理的信息输出
 * @author Lucifaer
 * @version 3.0
 */
public class JokerException extends Throwable {
    protected String message;

    public JokerException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Error{" +
                "message='" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }
}
