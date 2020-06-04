package com.lucifaer.jokerframework.joker.core.error.core;

import com.lucifaer.jokerframework.joker.core.error.JokerException;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/3
 */
public class ModelNotFound extends JokerException {
    public ModelNotFound(String modelName) {
        super("No " + modelName + " name " + modelName + "Name.");
    }
}
