package com.lucifaer.jokerframework.core.shell.command;

import com.lucifaer.jokerframework.core.shell.config.ShellHelper;
import com.lucifaer.jokerframework.data.JokerContext;
import com.lucifaer.jokerframework.data.ShellContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.HashMap;
import java.util.Map;

@ShellComponent
public class UseCommand {
    @Autowired
    JokerContext jokerContext;
    @Autowired
    ShellHelper shellHelper;

    @ShellMethod(value = "use exploit model", key = "use", group = "Joker")
    public String doUse(String exploitName) {

        ShellContext shellContext = shellContext();
        Map<String, String> params = new HashMap<>();
        shellContext.commandNode.push("use");
        shellContext.contextName = exploitName;

        shellContext.setParams(params);
        jokerContext.shellRegister(shellContext);
        return shellHelper.getSuccessMessage(String.format("Hello %s", exploitName));
    }

    @ShellMethod(value = "run exploit", key = "exploit", group = "Joker")
    public void doExploit() {

    }

    @Lookup
    protected ShellContext shellContext() {
        return null;
    }
}
