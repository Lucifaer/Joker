package com.lucifaer.jokerframework.core.exception;

public class ParamsNotFound extends JokerException {
    public ParamsNotFound(String expectedParam) {
        this.message = expectedParam + " is not found";
    }
}
