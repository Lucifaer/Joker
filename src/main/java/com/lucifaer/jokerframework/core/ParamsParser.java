package com.lucifaer.jokerframework.core;

import com.lucifaer.jokerframework.core.command.ExploitCommand;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(description = "Java Application Exploit Framework", name = "Joker", mixinStandardHelpOptions = true)
public class ParamsParser implements Callable {

    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new ParamsParser()).addSubcommand("exploit", new ExploitCommand());
        commandLine.execute(args);
    }

    @Override
    public Object call() throws Exception {
        return null;
    }
}
