package com.lucifaer.jokerframework.core.shell.commands;

import com.lucifaer.jokerframework.data.core.ShellDataModel;
import com.lucifaer.jokerframework.utils.ShellResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ServerApp {
    @Autowired
    private ShellDataModel shellDataModel;

    @ShellMethod(value = "run exploit server", key = "server", group = "Joker")
    public ShellResultHandler doServer(String serverType) {
        shellDataModel.getParams().put("serverType", serverType);
        shellDataModel.getPreCommandNode().push(shellDataModel.getCurrentCommandNode());
        shellDataModel.setCurrentCommandNode("server");
        return new ShellResultHandler(serverType, "server");
    }
}
