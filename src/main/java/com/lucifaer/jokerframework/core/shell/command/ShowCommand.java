package com.lucifaer.jokerframework.core.shell.command;

import com.lucifaer.jokerframework.core.factory.ExploitFactory;
import com.lucifaer.jokerframework.core.shell.config.JokerShellHelper;
import com.lucifaer.jokerframework.modules.Exploit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;

@ShellComponent
public class ShowCommand {
    @Autowired
    JokerShellHelper jokerShellHelper;

    @Autowired
    ExploitFactory exploitFactory;

    @ShellMethod(value = "show configurations", key = "show_options", group = "Joker")
    @ShellMethodAvailability("isUsed")
    public void doShow() throws Exception {
        Exploit exploit = exploitFactory.getObject();
        List<String[]> result = exploit.documentation();
        for (String[] option : result) {
            for (String s : option) {
                jokerShellHelper.echoDocument(s);
            }
        }
    }
}
