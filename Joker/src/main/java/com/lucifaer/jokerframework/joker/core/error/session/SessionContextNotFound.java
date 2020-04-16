package com.lucifaer.jokerframework.joker.core.error.session;

import com.lucifaer.jokerframework.joker.core.error.JokerException;

/**
 * 处理SessionContext不存在的异常
 * @author Lucifaer
 * @version 3.0
 */
public class SessionContextNotFound extends JokerException {
    public SessionContextNotFound(String sessionId) {
        super(sessionId + " is not found in SessionContext");
    }
}
