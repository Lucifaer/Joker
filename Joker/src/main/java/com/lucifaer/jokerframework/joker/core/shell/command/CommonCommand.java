package com.lucifaer.jokerframework.joker.core.shell.command;

import com.lucifaer.jokerframework.joker.core.context.JokerContext;
import com.lucifaer.jokerframework.joker.core.task.ExploitTask;
import com.lucifaer.jokerframework.sdk.context.ShellContext;
import com.lucifaer.jokerframework.joker.core.shell.ShellHelper;
import com.lucifaer.jokerframework.joker.core.task.CommonTask;
import com.lucifaer.jokerframework.joker.core.task.ServerTask;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Map;

/**
 * @author Lucifaer
 * @version 3.0
 */
@ShellComponent
public class CommonCommand {
    private final JokerContext jokerContext;
    private final CommonTask commonTask;
    private final ServerTask serverTask;
    private final ExploitTask exploitTask;
    private final ShellHelper shellHelper;

    public CommonCommand(JokerContext jokerContext, CommonTask commonTask, ServerTask serverTask, ExploitTask exploitTask, ShellHelper shellHelper) {
        this.jokerContext = jokerContext;
        this.commonTask = commonTask;
        this.serverTask = serverTask;
        this.exploitTask = exploitTask;
        this.shellHelper = shellHelper;
    }

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
                for (String existServer : serverTask.listExistTask().keySet()) {
                    shellHelper.echoDocument(existServer);
                }
                break;
            case "session":
                for (String existSession : commonTask.listTask().keySet()) {
                    shellHelper.echoDocument(existSession);
                }
                break;
            case "server_process":
                Map<String, Map> serverMap = serverTask.listTask();
                for (String serverProcess : serverMap.keySet()) {
                    shellHelper.echoDocument("serverId: " + serverProcess +
                            "\t\tserverName: " + serverMap.get(serverProcess).get("serverName") +
                            "\tserverPort: " + serverMap.get(serverProcess).get("serverPort"));
                }
                break;
            case "exploit":
                for (String existExploit : exploitTask.listTask().keySet()) {
                    shellHelper.echoDocument(existExploit);
                }
                break;
            default:
                shellHelper.echoInfo("list task");
        }
    }
}
