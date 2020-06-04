package com.lucifaer.jokerframework.joker.core.error.core;

import com.lucifaer.jokerframework.joker.core.error.JokerException;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/3
 */
public class SessionContextNameAlreadyUsed extends JokerException {
    public SessionContextNameAlreadyUsed(String contextName) {
        super(contextName + " is already in use. Please change another name.");
    }
}
