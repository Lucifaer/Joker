package com.lucifaer.jokerframework.core.shell.config;

import com.lucifaer.jokerframework.data.JokerContext;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;

public class DefaultDocumentation {
    @Autowired
    JokerContext jokerContext;

    public String[] defaultExploitDocumentation() {
        return new String[] {
                "Exploit configurations: ",
                "   [targetUrl]                 " + jokerContext.getCurrentShellContext().getParams().get("targetUrl") + "\t\ttarget url. eg: 127.0.0.1:23333",
        };
    }

    public String[] defaultPayloadDocumentation() {
        return new String[] {
                "Payload configurations: ",
                "   [command]                   " + jokerContext.getCurrentShellContext().getParams().get("command") + "\t\twhat command do you wanna execute. eg: whoami",
        };
    }

    public String[] defaultServerDocumentation() {
        return new String[] {
                "Server configurations: ",
                "   [serverType]                " + jokerContext.getCurrentShellContext().getParams().get("serverType" + "\t\tserver's type. eg: http"),
                "   [serverUrl]                 " + jokerContext.getCurrentShellContext().getParams().get("serverUrl") + "\t\tserver's url eg: http://127.0.0.1. default: http://0.0.0.0",
                "   [serverPort]                " + jokerContext.getCurrentShellContext().getParams().get("serverPort") + "\t\tserver's port. eg: 9999. default: 9999",
        };
    }
}
