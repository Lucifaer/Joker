package com.lucifaer.jokerframework.joker.core.error.core;

import com.lucifaer.jokerframework.joker.core.error.JokerException;

import java.util.List;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/3
 */
public class ParamLost extends JokerException {
    public ParamLost(List<String> lostParams) {
        super("Lost these params: " + lostParams.toString());
    }
}
