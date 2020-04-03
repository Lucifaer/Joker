package com.lucifaer.jokerframework.core.server;

import java.util.HashMap;
import java.util.Map;

public abstract class JokerBaseServer implements JokerServer {
    protected String serverUrl;
    protected String serverPort;
    protected String serverName;

    protected Map<String, String> expactedParams = new HashMap<>();

    public String getServerUrl() {
        return serverUrl;
    }

    @Override
    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Override
    public String getServerPort() {
        return serverPort;
    }

    @Override
    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public Map<String, String> getExpectedParams() {
        return expactedParams;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }


}
