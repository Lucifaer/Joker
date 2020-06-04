package com.lucifaer.jokerframework.joker.core.error.core;

import com.lucifaer.jokerframework.joker.core.error.JokerException;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/3
 */
public class SessionContextNotFound extends JokerException {
    public SessionContextNotFound(String sessionId) {
        super(sessionId + " is not found in SessionContext.");
    }
}
