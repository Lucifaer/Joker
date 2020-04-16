package com.lucifaer.jokerframework.joker.core.shell.command;

import com.lucifaer.jokerframework.joker.core.context.JokerContext;
import com.lucifaer.jokerframework.sdk.context.ShellContext;
import com.lucifaer.jokerframework.joker.core.shell.ShellHelper;
import com.lucifaer.jokerframework.joker.core.task.ServerTask;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lucifaer
 * @version 3.0
 */
@ShellComponent
public class ServerCommand {
    private final JokerContext jokerContext;
    private final ServerTask serverTask;
    private final ShellHelper shellHelper;

    public ServerCommand(JokerContext jokerContext, ServerTask serverTask, ShellHelper shellHelper) {
        this.jokerContext = jokerContext;
        this.serverTask = serverTask;
        this.shellHelper = shellHelper;
    }

    @ShellMethod(value = "init server work context", key = "server", group = "Server")
    public String initServerContext(String sessionId) {
        ShellContext shellContext = shellContext();
        Map<String, String> params = new HashMap<>();

        shellContext.commandNode.push("server");
        shellContext.setContextName(sessionId);
        shellContext.setContextType("server");
        shellContext.setParams(params);

        jokerContext.sessionRegister(shellContext);

        jokerContext.currentShell = shellContext;
        return shellHelper.getSuccessMessage("Create server context " + sessionId + " success!");
    }

    @ShellMethod(value = "start server", key = "run", group = "Server")
    public void run(String sessionId) {
        ShellContext shellContext = (ShellContext) jokerContext.getSessionMap().get(sessionId);
        serverTask.createTask(shellContext);
    }

    @ShellMethod(value = "stop server", key = "stop", group = "Server")
    public void stop(String sessionId) {
        ShellContext shellContext = (ShellContext) jokerContext.getSessionMap().get(sessionId);
        serverTask.stopTask(shellContext);
    }

    @Lookup
    protected ShellContext shellContext() {
        return null;
    }
}
