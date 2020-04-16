package com.lucifaer.jokerframework.joker.core.error.session;

import com.lucifaer.jokerframework.joker.core.error.JokerException;

/**
 * 处理SessionContext名称重复的异常
 * @author Lucifaer
 * @version 3.0
*/
public class SessionContextNameAlreadyUsed extends JokerException {
    public SessionContextNameAlreadyUsed(String contextName) {
        super(contextName + " is already in use. Please change another name.");
    }
}
