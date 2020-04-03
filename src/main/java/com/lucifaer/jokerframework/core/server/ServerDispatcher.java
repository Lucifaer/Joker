package com.lucifaer.jokerframework.core.server;

import com.lucifaer.jokerframework.core.context.JokerContext;
import com.lucifaer.jokerframework.core.context.ShellContext;
import com.lucifaer.jokerframework.core.exception.JokerException;
import com.lucifaer.jokerframework.core.exception.ServerNotFound;
import com.lucifaer.jokerframework.core.factory.ServerFactory;
import com.lucifaer.jokerframework.core.filter.ServerFilter;
import com.lucifaer.jokerframework.core.shell.ShellThrowableHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServerDispatcher {
    @Autowired
    JokerContext jokerContext;

    @Autowired
    ServerFactory serverFactory;

    @Autowired
    ServerFilter serverFilter;

    @Autowired
    ShellThrowableHandler shellThrowableHandler;

    public JokerServer dispatch(ShellContext shellContext) {
        JokerServer server = null;
        try {
            if (serverFilter.basicFilter(shellContext)) {
                String serverName = shellContext.getParams().get("serverName");
                for (String existkey : serverFactory.getExistMap().keySet()) {
                    if (serverName.equals(existkey)) {
                        server = serverFactory.getExistMap().get(serverName);
                    }
                }

                if (server == null) {
                    throw new ServerNotFound(serverName);
                }
            }
        } catch (JokerException e) {
            shellThrowableHandler.handleThrowable(e);
        }
        return server;
    }
}
