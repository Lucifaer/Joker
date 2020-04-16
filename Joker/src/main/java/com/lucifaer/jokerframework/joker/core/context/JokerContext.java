package com.lucifaer.jokerframework.joker.core.context;

import com.lucifaer.jokerframework.joker.core.error.server.ServerContextNameAlreadyUsed;
import com.lucifaer.jokerframework.joker.core.error.server.ServerContextNameNotFound;
import com.lucifaer.jokerframework.joker.core.error.session.SessionContextNameAlreadyUsed;
import com.lucifaer.jokerframework.joker.core.error.session.SessionContextNotFound;
import com.lucifaer.jokerframework.joker.core.server.Server;
import com.lucifaer.jokerframework.joker.core.shell.ShellThrowableHandler;
import com.lucifaer.jokerframework.sdk.context.Context;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * JokerContext为应用的根Context，包含了所有其他类型Context的内容，其中包含以下几个子Context：
 * <p>1. contextMap:全局上下文，包括sessionMap、serverMap</p>
 * <p>2. sessionMap:任务上下文，主要用于保存任务状态</p>
 * <p>3. serverMap:服务上下文，主要用于保存开启的服务别名及其端口信息</p>
 * @author Lucifaer
 * @version 3.0
 */
@Component
public class JokerContext implements Context {
    private ShellThrowableHandler shellThrowableHandler;

    private String contextName = "root";
    private String contextType = "root";
    public Context currentShell = null;

    private Map<String, Map> contextMap = new HashMap<>();
    private Map<String, Context> sessionMap = new HashMap<>();
    private Map<String, Map> serverMap = new HashMap<>();
    
    /**
     * sessionRegister方法用于任务上下文的注册
     * @param context 被注册的Context
     * @throws SessionContextNameAlreadyUsed SessionContext名称重复
     * @see SessionContextNameAlreadyUsed
     * @author Lucifaer
     * @version 3.0
    */
    public void sessionRegister(Context context) {
        try {
            String contextName = context.getContextName();
            if (this.sessionMap.containsKey(contextName)) {
                throw new SessionContextNameAlreadyUsed(contextName);
            }
            else {
                this.sessionMap.put(contextName, context);
                this.currentShell = context;
                this.contextMap.put("session", this.sessionMap);
            }
        } catch (SessionContextNameAlreadyUsed sessionContextNameAlreadyUsed) {
            shellThrowableHandler.handleThrowable(sessionContextNameAlreadyUsed);
        }
    }

    /**
     * sessionUnRegister方法用于删除已注册的任务上下文
     * @param sessionId 需要被删除的任务上下文id
     * @throws SessionContextNotFound SessionContext不存在
     * @see SessionContextNotFound
     * @author Lucifaer
     * @version 3.0
    */
    public void sessionUnRegister(String sessionId) {
        try {
            if (sessionMap.containsKey(sessionId)) {
                this.sessionMap.remove(sessionId);
            }
            else {
                throw new SessionContextNotFound(sessionId);
            }
        } catch (SessionContextNotFound sessionContextNotFound) {
            shellThrowableHandler.handleThrowable(sessionContextNotFound);
        }
    }

    public void serverRegister(String serverId, Server server) {
        try {
            if (serverMap.containsKey(serverId)) {
                throw new ServerContextNameAlreadyUsed(serverId);
            }
            else {
                Map<String, String> serverInfo = new HashMap<>();
                serverInfo.put("serverName", server.getServerName());
                serverInfo.put("serverPort", server.getServerPort());
                serverMap.put(serverId, serverInfo);
                contextMap.put("server", serverMap);
            }
        } catch (ServerContextNameAlreadyUsed serverContextNameAlreadyUsed) {
            shellThrowableHandler.handleThrowable(serverContextNameAlreadyUsed);
        }
    }

    public void serverUnRegister(String serverId) {
        try {
            if (serverMap.containsKey(serverId)) {
                serverMap.remove(serverId);
            }
            else {
                throw new ServerContextNameNotFound(serverId);
            }
        } catch (ServerContextNameNotFound serverContextNameNotFound) {
            shellThrowableHandler.handleThrowable(serverContextNameNotFound);
        }
    }

    public Map<String, Map> getContextMap() {
        return contextMap;
    }

    public Map<String, Context> getSessionMap() {
        return sessionMap;
    }

    public Map<String, Map> getServerMap() {
        return serverMap;
    }

    public void setContextType(String contextType) {
        this.contextType = contextType;
    }

    @Override
    public String getContextName() {
        return contextName;
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
