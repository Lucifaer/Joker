package com.lucifaer.jokerframework.core.shell.command;

import com.lucifaer.jokerframework.core.context.Context;
import com.lucifaer.jokerframework.core.context.JokerContext;
import com.lucifaer.jokerframework.core.context.ShellContext;
import com.lucifaer.jokerframework.core.exception.SessionNotFound;
import com.lucifaer.jokerframework.core.shell.ShellHelper;
import com.lucifaer.jokerframework.core.task.CommonJokerTask;
import com.lucifaer.jokerframework.core.task.ExploitJokerTask;
import com.lucifaer.jokerframework.core.task.ServerJokerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Map;

@ShellComponent
public class CommonCommand {
    @Autowired
    JokerContext jokerContext;

    @Autowired
    ShellHelper shellHelper;

    @Autowired
    ServerJokerTask serverJokerTask;

    @Autowired
    CommonJokerTask commonTask;

    @Autowired
    ExploitJokerTask exploitJokerTask;

    @ShellMethod(value = "set param", key = "set", group = "Common")
    public String set(String config, String value) {
        ShellContext shellContext = (ShellContext) jokerContext.currentShell;
        shellContext.getParams().put(config, value);
        return shellHelper.getInfoMessage(String.format("set %s with %s", config, value));
    }

    @ShellMethod(value = "list exist mods", key = "list", group = "Common")
    public void list(String type) {
        switch (type) {
            case "server":
                for (String existServer : serverJokerTask.listExistTask().keySet()) {
                    shellHelper.echoDocument(existServer);
                }
                break;
            case "session":
                for (String existSession : commonTask.listTask().keySet()) {
                    shellHelper.echoDocument(existSession);
                }
                break;
            case "server_process":
                Map<String, Map> serverMap = serverJokerTask.listTask();
                for (String serverProcess : serverMap.keySet()) {
                    shellHelper.echoDocument("sessionId: " + serverProcess +
                            "\t\tserverName: " + serverMap.get(serverProcess).get("serverName") +
                            "\tserverPort: " + serverMap.get(serverProcess).get("serverPort"));
                }
                break;
            case "exploit":
                for (String existExploit : exploitJokerTask.listTask().keySet()) {
                    shellHelper.echoDocument(existExploit);
                }
                break;
            default:
                shellHelper.echoInfo("list server");
        }
    }

    @ShellMethod(value = "checkout a session", key = "checkout", group = "Common")
    public void checkout(String sessionId) throws SessionNotFound {
        Map<String, Context> sessionMap = jokerContext.getSessionMap();
        if (sessionMap.containsKey(sessionId)) {
            jokerContext.currentShell = sessionMap.get(sessionId);
        }
        else {
            throw new SessionNotFound(sessionId);
        }
    }

    @ShellMethod(value = "exit current session", key = "q", group = "Common")
    public void q() {
        jokerContext.currentShell = null;
    }

    @ShellMethod(value = "delete ")

    @Lookup
    protected ShellContext shellContext() {
        return null;
    }
}
