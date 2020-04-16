package com.lucifaer.jokerframework.joker.core.error.server;

import com.lucifaer.jokerframework.joker.core.error.JokerException;

/**
 * @author Lucifaer
 * @version 3.0
 */
public class ServerContextNameNotFound extends JokerException {
    public ServerContextNameNotFound(String serverId) {
        super(serverId + " is not found in ServerContext.");
    }
}
