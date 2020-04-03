package com.lucifaer.jokerframework.core.task;

import com.lucifaer.jokerframework.core.context.Context;
import com.lucifaer.jokerframework.core.context.JokerContext;
import com.lucifaer.jokerframework.core.context.ShellContext;
import com.lucifaer.jokerframework.core.exception.JokerException;
import com.lucifaer.jokerframework.core.factory.ServerFactory;
import com.lucifaer.jokerframework.core.filter.ServerFilter;
import com.lucifaer.jokerframework.core.server.JokerServer;
import com.lucifaer.jokerframework.core.server.ServerDispatcher;
import com.lucifaer.jokerframework.core.shell.ShellHelper;
import com.lucifaer.jokerframework.core.shell.ShellThrowableHandler;
import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

@Component
public class ServerJokerTask implements JokerTask {
    @Autowired
    JokerContext jokerContext;

    @Autowired
    ShellHelper shellHelper;

    @Autowired
    ServerFactory serverFactory;

    @Autowired
    ServerDispatcher serverDispatcher;

    @Autowired
    ServerFilter serverFilter;

    @Autowired
    ShellThrowableHandler shellThrowableHandler;

    @Autowired
    @Qualifier("serviceExecutor")
    Executor executor;

    @Override
    public void createTask(ShellContext shellContext) {
        JokerServer server;
        try {
            server = serverDispatcher.dispatch(shellContext);
            List<String> errorParams = serverFilter.paramsFilter(server, shellContext);
            if (errorParams.isEmpty()) {
                server.createServer(shellContext);
                jokerContext.serverRegister(shellContext.getContextName(), server);
                shellHelper.getSuccessMessage("Create " + server.getServerName() + " Successful!");
            }
        } catch (Throwable e) {
            shellThrowableHandler.handleThrowable(e);
        }

    }

    @Override
    public Context attachTask(String processId) {
        return null;
    }

    @Override
    public void stopTask(ShellContext shellContext) {
        JokerServer server;
        try {
            server = serverDispatcher.dispatch(shellContext);
            server.stopServer();
            jokerContext.serverUnRegister(shellContext.getContextName());
            shellHelper.getSuccessMessage("Stop " + shellContext.getParams().get("serverName") + " Successful!");
        } catch (JokerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Map> listTask() {
        return jokerContext.getServerMap();
    }

    public Map<String, JokerServer> listExistTask() {
        return serverFactory.getExistMap();
    }
}
