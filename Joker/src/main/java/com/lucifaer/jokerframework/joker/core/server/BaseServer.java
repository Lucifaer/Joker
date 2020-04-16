package com.lucifaer.jokerframework.joker.core.server;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseServer抽象类负责提供Server基础的配置参数及其getter/setter方法
 * @author Lucifaer
 * @version 3.0
 */
public abstract class BaseServer implements Server {
    protected String modelTypeName = "server";
    protected String serverUrl;
    protected String serverPort;
    protected String serverName;
    protected List<String> requiredParams = new ArrayList<>();

    @Override
    public String getModelTypeName() {
        return modelTypeName;
    }

    @Override
    public String getName() {
        return serverName;
    }

    @Override
    public String getServerName() {
        return serverName;
    }

    @Override
    public String getServerPort() {
        return serverPort;
    }

    @Override
    public List<String> getRequiredParams() {
        return requiredParams;
    }

    @Override
    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Override
    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }
}
