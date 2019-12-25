package com.lucifaer.jokerframework.core.shell.commands;

import com.lucifaer.jokerframework.data.core.ShellDataModel;
import com.lucifaer.jokerframework.utils.ShellResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class UseApp {
    @Autowired
    private ShellDataModel shellDataModel;

    private boolean used = false;

    @ShellMethod(value = "use exploit Model", key = "use", group = "Joker")
    public ShellResultHandler doUse(String exploitName) {
        shellDataModel.getParams().put("exploitName", exploitName);
        shellDataModel.getPreCommandNode().push(shellDataModel.getCurrentCommandNode());
        shellDataModel.setCurrentCommandNode("exploit");
        used = true;
        return new ShellResultHandler(exploitName, "use");
    }
}
