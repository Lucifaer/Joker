package com.lucifaer.jokerframework.core.shell.command;

import com.lucifaer.jokerframework.core.shell.config.JokerCommandManager;
import com.lucifaer.jokerframework.core.shell.config.JokerShellProvider;
import com.lucifaer.jokerframework.core.shell.config.ShellHelper;
import com.lucifaer.jokerframework.data.JokerContext;
import com.lucifaer.jokerframework.data.ShellContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@Lazy
public class CommonCommand extends JokerCommandManager {
    @Autowired
    JokerContext jokerContext;
    @Autowired
    ShellHelper shellHelper;

    private ShellContext shellContext;

    @ShellMethod(value = "set exploit options", key = "set", group = "Joker")
    @ShellMethodAvailability("isUsed")
    public String doSet(@ShellOption(valueProvider = JokerShellProvider.class) String config, String value) {
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

    @ShellMethod(value = "list exist type mod", key = "list", group = "Joker")
    public void doList(String type) {
        if ("exploit".equals(type)) {
            for (String existExploitName : jokerContext.getExistExploitMap().keySet()) {
                shellHelper.echoDocument(existExploitName);
            }
        }
        else if ("server".equals(type)) {
        }
    }

    private void stackHandler(String node) {
        if (!shellContext.commandNode.peek().equals(node)) {
            shellContext.commandNode.push(node);
        }
    }
}
