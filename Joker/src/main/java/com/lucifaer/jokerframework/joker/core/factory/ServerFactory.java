package com.lucifaer.jokerframework.joker.core.factory;

import com.lucifaer.jokerframework.joker.core.server.Server;
import org.springframework.stereotype.Component;

/**
 * @author Lucifaer
 * @version 3.0
 */
@Component
public class ServerFactory extends BaseFactory<Server> {
    public ServerFactory() {
        this.setBeansOfType(Server.class);
    }
}
