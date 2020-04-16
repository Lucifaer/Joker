package com.lucifaer.jokerframework.joker.core.error.core;

import com.lucifaer.jokerframework.joker.core.error.JokerException;

/**
 * @author Lucifaer
 * @version 3.0
 */
public class ModelNotFound extends JokerException {
    public ModelNotFound(String modelName) {
        super("No " + modelName + " name " + modelName + "Name");
    }
}
