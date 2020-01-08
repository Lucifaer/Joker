package com.lucifaer.jokerframework.core.shell.command;

import com.lucifaer.jokerframework.core.factory.ExploitFactory;
import com.lucifaer.jokerframework.core.shell.config.ShellHelper;
import com.lucifaer.jokerframework.data.JokerContext;
import com.lucifaer.jokerframework.modules.Exploit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;

@ShellComponent
public class ShowCommand {
    @Autowired
    ShellHelper shellHelper;

    @Autowired
    ExploitFactory exploitFactory;

//    @Lazy
//    @Autowired
//    Exploit exploit;

    @ShellMethod(value = "show configurations", key = "show_options", group = "Joker")
    @ShellMethodAvailability("isUsed")
    public void doShow() throws Exception {
        Exploit exploit = (Exploit) exploitFactory.getObject();
        List<String[]> result = exploit.documentation();
        for (String[] option : result) {
            for (String s : option) {
                shellHelper.echoDocument(s);
            }
        }
    }
}
