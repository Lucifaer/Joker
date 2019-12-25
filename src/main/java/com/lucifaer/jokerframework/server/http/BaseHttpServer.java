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
        if (serverUrl.isEmpty()) {
            this.serverUrl = "http://0.0.0.0";
        }

        if (serverPort.isEmpty()) {
            this.serverPort = "9999";
        }
    }

    public void createServer() {
        try {
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(this.serverPort)), 0);
            server.createContext("/", handler);
            server.setExecutor(null);
            server.start();
            Thread.sleep(10000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        server.stop(-1);
    }

    public HttpHandler getHandler() {
        return handler;
    }

    void setHandler(HttpHandler handler) {
        this.handler = handler;
    }

    String getServerUrl() {
        return serverUrl;
    }

    void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    String getServerPort() {
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
