package com.lucifaer.jokerframework.joker.core.task;

import com.lucifaer.jokerframework.joker.core.context.JokerContext;
import com.lucifaer.jokerframework.sdk.context.ShellContext;
import com.lucifaer.jokerframework.joker.core.dispatcher.ServerDispatcher;
import com.lucifaer.jokerframework.joker.core.factory.BaseFactory;
import com.lucifaer.jokerframework.joker.core.server.Server;
import com.lucifaer.jokerframework.joker.core.shell.ShellHelper;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Lucifaer
 * @version 3.0
 */
@Component
public class ServerTask implements Task {
    private final ServerDispatcher serverDispatcher;
    private final BaseFactory<Server> serverFactory;
    private final JokerContext jokerContext;
    private final ShellHelper shellHelper;

    public ServerTask(ServerDispatcher serverDispatcher, BaseFactory<Server> serverFactory, JokerContext jokerContext, ShellHelper shellHelper) {
        this.serverDispatcher = serverDispatcher;
        this.serverFactory = serverFactory;
        this.jokerContext = jokerContext;
        this.shellHelper = shellHelper;
    }

    @Override
    public void createTask(ShellContext shellContext) {
        Server server;
        server = serverDispatcher.startDispatcher(shellContext);

        server.createServer(shellContext);
        jokerContext.serverRegister(shellContext.getContextName(), server);
        shellHelper.echoSuccess("Create " + server.getServerName() + " Success!");
    }

    @Override
    public void stopTask(ShellContext shellContext) {
        Server server;
        server = serverDispatcher.stopDispatcher(shellContext);
        server.stopServer();
        jokerContext.serverUnRegister(shellContext.getContextName());
        shellHelper.echoSuccess("Stop " + shellContext.getParams().get("serverName"));
    }

    @Override
    public Map listTask() {
        return jokerContext.getServerMap();
    }

    public Map<String, Server> listExistTask() {
        return serverFactory.getExistMap();
    }
}
