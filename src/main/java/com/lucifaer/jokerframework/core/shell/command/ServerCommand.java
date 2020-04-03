package com.lucifaer.jokerframework.core.shell.command;

import com.lucifaer.jokerframework.core.context.JokerContext;
import com.lucifaer.jokerframework.core.context.ShellContext;
import com.lucifaer.jokerframework.core.exception.SessionContextNameAlreadyUsed;
import com.lucifaer.jokerframework.core.shell.ShellHelper;
import com.lucifaer.jokerframework.core.task.ServerJokerTask;
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
    ServerJokerTask serverJokerTask;

    @ShellMethod(value = "init server work context", key = "server", group = "Server")
    public String initServerContext(String sessionId) {
        ShellContext shellContext = shellContext();

        Map<String, String> params = new HashMap<>();

        shellContext.commandNode.push("server");
        shellContext.setContextName(sessionId);
        shellContext.setContextType("server");
        shellContext.setParams(params);
        try {
            jokerContext.sessionRegister(shellContext);
        }catch (SessionContextNameAlreadyUsed e) {
            shellHelper.getErrorMessage(e.toString());
        }
        jokerContext.currentShell = shellContext;
        return shellHelper.getInfoMessage("Create server context " + sessionId + " successfully");
    }

    @ShellMethod(value = "start server", key = "run", group = "Server")
    public void run(String sessionId) {
        ShellContext context = (ShellContext) jokerContext.getSessionMap().get(sessionId);
        serverJokerTask.createTask(context);
    }

    @ShellMethod(value = "stop server", key = "stop", group = "Server")
    public void stop(String sessionId) {
        ShellContext context = (ShellContext) jokerContext.getSessionMap().get(sessionId);
        serverJokerTask.stopTask(context);
    }

    @Lookup
    protected ShellContext shellContext() {
        return null;
    }
}
