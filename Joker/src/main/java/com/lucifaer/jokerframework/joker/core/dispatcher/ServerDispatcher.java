package com.lucifaer.jokerframework.joker.core.dispatcher;

import com.lucifaer.jokerframework.sdk.context.ShellContext;
import com.lucifaer.jokerframework.joker.core.factory.BaseFactory;
import com.lucifaer.jokerframework.joker.core.filter.BaseFilter;
import com.lucifaer.jokerframework.joker.core.server.Server;
import com.lucifaer.jokerframework.joker.core.server.ServerModel;
import com.lucifaer.jokerframework.joker.core.shell.ShellThrowableHandler;
import org.springframework.stereotype.Component;

/**
 * @author Lucifaer
 * @version 3.0
 */
@Component
public class ServerDispatcher extends BaseDispatcher<Server> {
    public ServerDispatcher(ServerModel model, BaseFactory<Server> factory, BaseFilter<Server> filter, ShellThrowableHandler shellThrowableHandler) {
        super(model, factory, filter, shellThrowableHandler);
    }

    public Server startDispatcher(ShellContext shellContext) {
        Server server = super.dispatcher(shellContext);
        filter.preCheck(server, shellContext);
        return server;
    }

    public Server stopDispatcher(ShellContext shellContext) {
        return super.dispatcher(shellContext);
    }
}
