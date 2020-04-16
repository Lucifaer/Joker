package com.lucifaer.jokerframework.joker.core.error.server;

import com.lucifaer.jokerframework.joker.core.error.JokerException;

/**
 * @author Lucifaer
 * @version 3.0
 */
public class ServerContextNameAlreadyUsed extends JokerException {
    public ServerContextNameAlreadyUsed(String serverId) {
        super("Already has an server context named " + serverId + ". Please change another name.");
    }
}
