package com.lucifaer.jokerframework.core.shell.command;

import com.lucifaer.jokerframework.core.factory.ServerFactory;
import com.lucifaer.jokerframework.core.shell.config.ShellHelper;
import com.lucifaer.jokerframework.data.CommandManagerContext;
import com.lucifaer.jokerframework.data.JokerContext;
import com.lucifaer.jokerframework.data.ShellContext;
import com.lucifaer.jokerframework.plugins.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.HashMap;
import java.util.Map;

@ShellComponent
public class ServerCommand {
    @Autowired
    JokerContext jokerContext;

    @Autowired
    ShellHelper shellHelper;

    @Autowired
    ServerFactory serverFactory;

    @ShellMethod(value = "use server model", key = "server", group = "Joker")
    public String doServer(String serverName) {
        ShellContext shellContext = shellContext();
        Map<String, String> params = new HashMap<>();
        params.put("serverName", serverName);
        shellContext.commandNode.push("server");
        shellContext.contextName = serverName;
        CommandManagerContext.setIsUsed(true);

        shellContext.setParams(params);
        jokerContext.shellRegister(shellContext);
        return shellHelper.getSuccessMessage(String.format("Setup %s server", serverName));
    }

    @ShellMethod(value = "start server", key = "run", group = "Joker")
    public void doRun() throws Exception {
        Server server = (Server) serverFactory.getObject();
        server.createServer();
    }

    @Lookup
    protected ShellContext shellContext() {
        return null;
    }
}
