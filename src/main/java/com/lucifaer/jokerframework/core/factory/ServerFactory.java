package com.lucifaer.jokerframework.core.factory;

import com.lucifaer.jokerframework.data.JokerContext;
import com.lucifaer.jokerframework.data.ShellContext;
import com.lucifaer.jokerframework.plugins.Server;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class ServerFactory implements ApplicationContextAware {
    @Autowired
    JokerContext jokerContext;

    private Map<String, Server> serverMap = new HashMap<>();

    public Server getObject() throws Exception {
        ShellContext shellContext = jokerContext.getCurrentShellContext();
        String paramsServerName = shellContext.getParams().get("serverName");
        for (String serverName : serverMap.keySet()) {
            if (paramsServerName.equals(serverName)) {
                return serverMap.get(serverName);
            }
        }
        throw new Exception("No server named " + paramsServerName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Server> map = applicationContext.getBeansOfType(Server.class);
        for (Map.Entry<String, Server> entry : map.entrySet()) {
            if (StringUtils.isEmpty(entry.getValue().getServerName())) {
                throw new RuntimeException("server name is empty: " + entry.getValue());
            }
            if (serverMap.get(entry.getValue().getServerName()) != null) {
                throw new RuntimeException("server " + entry.getValue().getServerName() + " already used by " + serverMap.get(entry.getValue().getServerName()));
            }
            serverMap.put(entry.getValue().getServerName(), entry.getValue());
        }
        jokerContext.setExistServerMap(serverMap);
    }
}
