package com.lucifaer.jokerframework.core.shell.command;

import com.lucifaer.jokerframework.core.factory.ExploitFactory;
import com.lucifaer.jokerframework.core.shell.config.JokerCommandManager;
import com.lucifaer.jokerframework.core.shell.config.ShellHelper;
import com.lucifaer.jokerframework.data.CommandManagerContext;
import com.lucifaer.jokerframework.data.JokerContext;
import com.lucifaer.jokerframework.data.ShellContext;
import com.lucifaer.jokerframework.modules.Exploit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.HashMap;
import java.util.Map;

@ShellComponent
public class UseCommand extends JokerCommandManager {
    @Autowired
    JokerContext jokerContext;

    @Autowired
    ShellHelper shellHelper;

    @Autowired
    ExploitFactory exploitFactory;

    @ShellMethod(value = "use exploit model", key = "use", group = "Joker")
    public String doUse(String exploitName) {
        if (!exploitFactory.exploitMap.containsKey(exploitName)) {
            return shellHelper.getErrorMessage("Can't find exploit mod named " + exploitName + ". Please use `list command to find exist command.`");
        }
        ShellContext shellContext = shellContext();
        Map<String, String> params = new HashMap<>();
        params.put("exploitName", exploitName);
        shellContext.commandNode.push("use");
        shellContext.contextName = exploitName;
        CommandManagerContext.setIsUsed(true);

        shellContext.setParams(params);
        jokerContext.shellRegister(shellContext);
        return shellHelper.getSuccessMessage(String.format("Use %s exploit", exploitName));
    }

    @ShellMethod(value = "run exploit", key = "exploit", group = "Joker")
    @ShellMethodAvailability("isUsed")
    public void doExploit() throws Exception {
        Exploit exploit = (Exploit) exploitFactory.getObject();
        exploit.exploit();
    }

    @Lookup
    protected ShellContext shellContext() {
        return null;
    }
}
