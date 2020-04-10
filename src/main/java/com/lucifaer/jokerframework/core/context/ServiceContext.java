package com.lucifaer.jokerframework.core.context;

import com.lucifaer.jokerframework.core.server.JokerServer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ServiceContext implements Context {
    private String contextName;
    private String contextType = "service";

    private Map<String, JokerServer> serviceContext = new HashMap<>();

    @Override
    public String getContextName() {
        return this.contextName;
    }

    @Override
    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    @Override
    public String getContextType() {
        return contextType;
    }
}
