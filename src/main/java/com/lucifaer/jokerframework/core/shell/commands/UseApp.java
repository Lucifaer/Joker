package com.lucifaer.jokerframework.core.shell.commands;

import com.lucifaer.jokerframework.data.core.ShellDataModel;
import com.lucifaer.jokerframework.modules.Exploit;
import com.lucifaer.jokerframework.utils.ShellResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class UseApp {
    @Autowired
    private ApplicationContext applicationContext;
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

    @ShellMethod(value = "run exploit", key = "exploit", group = "Joker")
    public void doExploit() {
        Exploit exploit = (Exploit) applicationContext.getBean("exploit");
        exploit.exploit();
    }
}
