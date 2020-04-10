package com.lucifaer.jokerframework.core.context;

import com.lucifaer.jokerframework.core.exception.ServerContextNameNotFound;
import com.lucifaer.jokerframework.core.exception.SessionContextNameAlreadyUsed;
import com.lucifaer.jokerframework.core.exception.ServerContextNameAlreadyUsed;
import com.lucifaer.jokerframework.core.exception.SessionContextNameNotFound;
import com.lucifaer.jokerframework.core.server.JokerServer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JokerContext implements Context {
    private String contextName = "root";
    private String contextType = "root";
    public Context currentShell = null;
//    全局上下文，包括sessionMap、serverMap
    private Map<String, Map> contextMap = new HashMap<>();
//    任务上下文，主要用于保存任务状态
    private Map<String, Context> sessionMap = new HashMap<>();
//    服务上下文，主要用于保存开启的服务别名及其端口信息
    private Map<String, Map> serverMap = new HashMap<>();

//  全局上下文注册
    public void sessionRegister(Context context) throws SessionContextNameAlreadyUsed {
        String contextName = context.getContextName();
        if (this.sessionMap.containsKey(contextName)) {
            throw new SessionContextNameAlreadyUsed(contextName);
        }else {
            this.sessionMap.put(contextName, context);
            this.currentShell = context;
            this.contextMap.put("session", this.sessionMap);
        }
    }

    public void sessionUnRegister(String sessionId) throws SessionContextNameNotFound {
        if (sessionMap.containsKey(sessionId)) {
            this.sessionMap.remove(sessionId);
        }
        else {
            throw new SessionContextNameNotFound(sessionId);
        }
    }

//  服务上下文注册
    public void serverRegister(String processId, JokerServer server) throws ServerContextNameAlreadyUsed {
        if (serverMap.containsKey(processId)) {
            throw new ServerContextNameAlreadyUsed(processId);
        }
        else {
            Map<String, String> serverInfo = new HashMap<>();
            serverInfo.put("serverName", server.getServerName());
            serverInfo.put("serverPort", server.getServerPort());
            this.serverMap.put(processId, serverInfo);
            this.contextMap.put("server", this.serverMap);
        }
    }

    public void serverUnRegister(String processId) throws ServerContextNameNotFound {
        if (serverMap.containsKey(processId)) {
            this.serverMap.remove(processId);
        }
        else {
            throw new ServerContextNameNotFound(processId);
        }
    }

    public Map<String, Map> getContextMap() {
        return contextMap;
    }

    public void setContextMap(Map<String, Map> contextMap) {
        this.contextMap = contextMap;
    }

    public Map<String, Context> getSessionMap() {
        return sessionMap;
    }

    public Map<String, Map> getServerMap() {
        return serverMap;
    }

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
        return this.contextType;
    }
}
