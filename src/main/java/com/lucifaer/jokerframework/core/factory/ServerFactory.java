package com.lucifaer.jokerframework.core.factory;

import com.lucifaer.jokerframework.core.server.JokerServer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ServerFactory extends JokerFactory<JokerServer> {
    public ServerFactory() {
        this.setBeansOfType(JokerServer.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        super.setApplicationContext(applicationContext);
    }
}
