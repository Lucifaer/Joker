package com.lucifaer.jokerframework.core.factory;

import com.lucifaer.jokerframework.data.JokerContext;
import com.lucifaer.jokerframework.data.ShellContext;
import com.lucifaer.jokerframework.plugins.Server;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ServerFactory implements FactoryBean, ApplicationContextAware {
    @Autowired
    JokerContext jokerContext;

    @Autowired
    ApplicationContext applicationContext;

//  TODO: 这一部分的代码与ExploitFactory逻辑相同，后续考虑进行合并
    public Map<String, Server> serverMap = new HashMap<>();

    @Override
    public Object getObject() throws Exception {
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
    public Class<?> getObjectType() {
        return Server.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
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
