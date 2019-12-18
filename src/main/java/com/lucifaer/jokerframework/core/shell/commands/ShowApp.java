package com.lucifaer.jokerframework.core.shell.commands;

import com.lucifaer.jokerframework.data.core.ShellDataModel;
import org.springframework.beans.factory.annotation.Autowired;

public class ShowApp {
    @Autowired
    private ShellDataModel shellContext;

    String[] defaultExploitConfigurations() {
        return new String[] {
                "Exploit configurations: ",
                "   [payloadName]               " + shellContext.getParams().get("payloadName") + "\t\twhich payload do you wanna set. eg: command",
                "   [targetUrl]                 " + shellContext.getParams().get("targetUrl") + "\t\ttarget url. eg: 127.0.0.1:23333",
        };
    }

    String[] defaultPayloadConfigurations() {
        return new String[] {
                "Payload configurations: ",
                "   [command]                   " + shellContext.getParams().get("command") +"\t\twhat command do you wanna execute. eg: whoami",
        };
    }

    String[] defaultServerConfigurations() {
        return new String[] {
                "Server configurations: ",
                "   [url]                       server's url. eg: http://127.0.0.1:9999. default: 0.0.0.0",
                "   [port]                      server's port. eg: 9999. default: 9999",
        };
    }
}