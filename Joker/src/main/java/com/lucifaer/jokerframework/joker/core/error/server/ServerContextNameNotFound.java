package com.lucifaer.jokerframework.joker.core.error.server;

import com.lucifaer.jokerframework.joker.core.error.JokerException;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/3
 */
public class ServerContextNameNotFound extends JokerException {
    public ServerContextNameNotFound(String serverId) {
        super(serverId + " is not found in ServerContext.");
    }
}
