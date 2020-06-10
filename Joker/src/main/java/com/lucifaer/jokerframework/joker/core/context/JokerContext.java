package com.lucifaer.jokerframework.joker.core.context;

import com.lucifaer.jokerframework.joker.core.error.core.SessionContextNameAlreadyUsed;
import com.lucifaer.jokerframework.joker.core.error.core.SessionContextNotFound;
import com.lucifaer.jokerframework.joker.core.error.server.ServerContextNameAlreadyUsed;
import com.lucifaer.jokerframework.joker.core.error.server.ServerContextNameNotFound;
import com.lucifaer.jokerframework.joker.core.server.Server;
import com.lucifaer.jokerframework.joker.core.shell.ShellThrowableHandler;
import com.lucifaer.jokerframework.sdk.api.Exploit;
import com.lucifaer.jokerframework.sdk.context.Context;
import com.lucifaer.jokerframework.sdk.context.ShellContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lucifaer
 * @version 4.1
 */
@Component
public class JokerContext implements Context {
    private ShellThrowableHandler shellThrowableHandler;

    public JokerContext(ShellThrowableHandler shellThrowableHandler) {
        this.shellThrowableHandler = shellThrowableHandler;
    }

    private String contextName = "root";
    private String contextType = "root";
    private Context currentShell = new ShellContext();

    private Map<String, Map> contextMap = new HashMap<>();
    private Map<String, Context> sessionMap = new HashMap<>();
    private Map<String, Map> serverMap = new HashMap<>();

    private Map<String, Exploit> cachedExploitMap = new HashMap<>();

    public void sessionRegister(Context context) {
        try {
            String contextName = context.getContextName();
            if (sessionMap.containsKey(contextName)) {
                throw new SessionContextNameAlreadyUsed(contextName);
            }
            else {
                sessionMap.put(contextName, context);
                currentShell = context;
                contextMap.put("session", sessionMap);
            }
        } catch (SessionContextNameAlreadyUsed sessionContextNameAlreadyUsed) {
            shellThrowableHandler.handleThrowable(sessionContextNameAlreadyUsed);
        }
    }

    public void sessionUnRegister(String sessionId) {
        try {
            if (sessionMap.containsKey(sessionId)) {
                sessionMap.remove(sessionId);
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

    public void setContextType(String contextType) {
        this.contextType = contextType;
    }

    public Context getCurrentShell() {
        return currentShell;
    }

    public void setCurrentShell(Context currentShell) {
        this.currentShell = currentShell;
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

    public void setSessionMap(Map<String, Context> sessionMap) {
        this.sessionMap = sessionMap;
    }

    public Map<String, Map> getServerMap() {
        return serverMap;
    }

    public void setServerMap(Map<String, Map> serverMap) {
        this.serverMap = serverMap;
    }

    public Map<String, Exploit> getCachedExploitMap() {
        return cachedExploitMap;
    }

    public void setCachedExploitMap(Map<String, Exploit> cachedExploitMap) {
        this.cachedExploitMap = cachedExploitMap;
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
