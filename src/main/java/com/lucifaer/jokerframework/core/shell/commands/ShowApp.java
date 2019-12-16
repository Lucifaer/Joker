package com.lucifaer.jokerframework.core.shell.commands;

import static com.lucifaer.jokerframework.data.JokerShellDataModel.BaseJokerShellDataModel.*;

public class ShowApp {
    public static String[] defaultExploitConfigurations() {
        return new String[] {
                "Exploit configurations: ",
                "   [payloadName]               " + params.get("payloadName") + "\t\twhich payload do you wanna set. eg: command",
                "   [targetUrl]                 " + params.get("targetUrl") + "\t\ttarget url. eg: 127.0.0.1:23333",
        };
    }

    public static String[] defaultPayloadConfigurations() {
        return new String[] {
                "Payload configurations: ",
                "   [command]                   " + params.get("command") +"\t\twhat command do you wanna execute. eg: whoami",
        };
    }

    public static String[] defaultServerConfigurations() {
        return new String[] {
                "Server configurations: ",
                "   [url]                       server's url. eg: http://127.0.0.1:9999. default: 0.0.0.0",
                "   [port]                      server's port. eg: 9999. default: 9999",
        };
    }
}
