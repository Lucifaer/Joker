package com.lucifaer.jokerframework.joker.core.error.core;

import com.lucifaer.jokerframework.joker.core.error.JokerException;

import java.util.List;

/**
 * @author Lucifaer
 * @version 3.0
 */
public class ParamLost extends JokerException {
    public ParamLost(List<String> lostParams) {
        super("Lost these params: " + lostParams.toString());
    }
}
