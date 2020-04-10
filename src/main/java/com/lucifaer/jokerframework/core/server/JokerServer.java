package com.lucifaer.jokerframework.core.server;

import com.lucifaer.jokerframework.core.context.ShellContext;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

public interface JokerServer {
    @Async("serviceExecutor")
    void createServer(ShellContext shellContext);

    void stopServer();

    String getServerName();
    String getServerPort();
    Map<String, String> getExpectedParams();

    void setServerUrl(String serverUrl);
    void setServerPort(String serverPort);
}
