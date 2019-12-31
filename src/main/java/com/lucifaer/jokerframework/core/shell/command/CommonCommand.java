package com.lucifaer.jokerframework.core.shell.command;

import com.lucifaer.jokerframework.core.shell.config.ShellHelper;
import com.lucifaer.jokerframework.data.JokerContext;
import com.lucifaer.jokerframework.data.ShellContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.ArrayList;

@ShellComponent
@Lazy
public class CommonCommand {
    @Autowired
    JokerContext jokerContext;
    @Autowired
    ShellHelper shellHelper;

    private ShellContext shellContext;

    @ShellMethod(value = "set exploit options", key = "set", group = "Joker")
    public String doSet(String config, String value) {
        this.shellContext = jokerContext.getCurrentShellContext();
        shellContext.getParams().put(config, value);
        if ("payloadName".equals(config)) {
            stackHandler("payload");
        }
        else {
            stackHandler("set");
        }
        return shellHelper.getSuccessMessage(String.format("set %s with %s", config, value));
    }

    @ShellMethod(value = "show configurations", key = "show_options", group = "Joker")
    public void doShow() {
        this.shellContext = jokerContext.getCurrentShellContext();
        ArrayList<String> result = new ArrayList<String>();
        if (shellContext.commandNode.isEmpty()) {
            result.add("use options");
        }
        else {
            for (String node : shellContext.commandNode) {
                if ("use".equals(node)) {
                    result.add("use options");
                }
                else if ("payload".equals(node)) {
                    result.add("payload options");
                }
            }
        }
        for (String option : result) {
            System.out.println(shellHelper.getInfoMessage(option));
        }
    }

    private void stackHandler(String node) {
        if (!shellContext.commandNode.peek().equals(node)) {
            shellContext.commandNode.push(node);
        }
    }
}
