package com.lucifaer.jokerframework.core.shell.command;

import com.lucifaer.jokerframework.core.shell.config.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class EchoCommand {
    @Autowired
    ShellHelper shellHelper;

    @ShellMethod(value = "test")
    public String echo(@ShellOption({"-N", "--name"}) String name) {
        String output = shellHelper.getSuccessMessage(String.format("Hello %s", name));
        return output.concat(" 123123");
    }
}
