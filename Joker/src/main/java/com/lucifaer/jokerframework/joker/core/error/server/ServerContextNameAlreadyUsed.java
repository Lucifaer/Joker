package com.lucifaer.jokerframework.joker.core.error.server;

import com.lucifaer.jokerframework.joker.core.error.JokerException;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/3
 */
public class ServerContextNameAlreadyUsed extends JokerException {
    public ServerContextNameAlreadyUsed(String serverId) {
        super("Already has an server context named " + serverId + ". Please change another name.");
    }
}
