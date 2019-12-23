package com.lucifaer.jokerframework.server;

import org.springframework.scheduling.annotation.Async;

public interface Server {
    @Async
    void createServer();
}
