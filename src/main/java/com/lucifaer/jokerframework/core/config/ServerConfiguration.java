package com.lucifaer.jokerframework.core.config;

import com.lucifaer.jokerframework.data.core.ShellDataModel;
import com.lucifaer.jokerframework.server.Client;
import com.lucifaer.jokerframework.server.Server;
import com.lucifaer.jokerframework.utils.commons.ClassUtil;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import java.util.Map;

import static com.lucifaer.jokerframework.utils.commons.ErrorHandler.printErroMsg;

//  TODO: 对于ClassUtil的调用有重复的部分，后续可以将其转换成utils工具类，但是有个比较冲突的问题是shellContext的调用问题，需要好好想想
@Configurable
@Import(ShellConfiguration.class)
public class ServerConfiguration {
    @Lazy
    @Bean
    public Server server(ShellDataModel shellContext) throws Exception {
        Map<String, Class> serverMap = ClassUtil.getAllClassByInterface(Server.class);
        String serverName = shellContext.getParams().get("serverName");
        String serverType = shellContext.getParams().get("serverType");
        Server server = null;

        for (String className : serverMap.keySet()) {
            String key = className.toLowerCase();
            if (key.startsWith(serverName+serverType)) {
                server = (Server) serverMap.get(className).newInstance();
            }
        }

        if (server == null) {
            printErroMsg(this.getClass(), "server is null, check your params!");
        }
        return server;
    }

    @Lazy
    @Bean
    public Client client(ShellDataModel shellContext) throws Exception {
        Map<String, Class> clientMap = ClassUtil.getAllClassByInterface(Client.class);
        String clientName = shellContext.getParams().get("exploitName");
        Client client = null;

        for (String className : clientMap.keySet()) {
            String key = className.toLowerCase();
            if (key.startsWith(clientName)) {
                client = (Client) clientMap.get(className).newInstance();
            }
        }

        if (client == null) {
            printErroMsg(this.getClass(), "client is null, check your params!");
        }
        return client;
    }
}