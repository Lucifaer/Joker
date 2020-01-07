package com.lucifaer.jokerframework.plugins.server.http;

import com.lucifaer.jokerframework.plugins.Server;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public abstract class BaseHttpServer implements Server {
    private HttpHandler handler;
    private String serverUrl;
    private String serverPort;
    private HttpServer server;

    private void init() {
        if (serverUrl == null || serverUrl.isEmpty()) {
            this.serverUrl = "http://0.0.0.0";
        }

        if (serverPort == null || serverPort.isEmpty()) {
            this.serverPort = "9999";
        }
    }

    public void createServer() {
        init();
        try {
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(this.serverPort)), 0);
            server.createContext("/", handler);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        server.stop(2);
    }

    void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    void setHandler(HttpHandler handler) {
        this.handler = handler;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public String getServerPort() {
        return serverPort;
    }
}
