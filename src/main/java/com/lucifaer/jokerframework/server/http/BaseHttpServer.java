package com.lucifaer.jokerframework.server.http;

import com.lucifaer.jokerframework.server.Server;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public abstract class BaseHttpServer implements Server {
    private HttpHandler handler = null;
    private String serverUrl = "";
    private String serverPort = "";
    protected HttpServer server = null;

    void init() {
        if (serverUrl == null || serverUrl.isEmpty()) {
            this.serverUrl = "http://0.0.0.0";
        }

        if (serverPort == null || serverPort.isEmpty()) {
            this.serverPort = "9999";
        }
    }

    public void createServer() {
        try {
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(this.serverPort)), 0);
            server.createContext("/", handler);
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        server.stop(2);
    }

    public HttpHandler getHandler() {
        return handler;
    }

    void setHandler(HttpHandler handler) {
        this.handler = handler;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getServerPort() {
        return serverPort;
    }

    void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public HttpServer getServer() {
        return server;
    }

    public void setServer(HttpServer server) {
        this.server = server;
    }
}
