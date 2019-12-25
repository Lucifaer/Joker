package com.lucifaer.jokerframework.server;

import com.sun.net.httpserver.HttpHandler;
import org.springframework.scheduling.annotation.Async;

public interface Server {
    @Async
    void createServer();

    void stopServer();

    String getServerUrl();

    String getServerPort();
}
