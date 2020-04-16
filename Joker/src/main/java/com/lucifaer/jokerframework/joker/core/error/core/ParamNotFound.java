package com.lucifaer.jokerframework.joker.core.error.core;

import com.lucifaer.jokerframework.joker.core.error.JokerException;

/**
 * @author Lucifaer
 * @version 3.0
 */
public class ParamNotFound extends JokerException {
    public ParamNotFound(String expectedParam) {
        super(expectedParam + " is not found!");
    }
}
