package com.lucifaer.jokerframework.core.shell.command;

import com.lucifaer.jokerframework.core.shell.ShellThrowableHandler;
import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Stacktrace;

@ShellComponent
public class StacktraceCommand implements Stacktrace.Command {
    @Autowired
    @Lazy
    Terminal terminal;

    @Autowired
    ShellThrowableHandler shellThrowableHandler;

    @ShellMethod(value = "Display the full stacktrace of the last error.", key = "stacktrace")
    public void stacktrace() {
        if (shellThrowableHandler.getLastError() != null){
            shellThrowableHandler.getLastError().printStackTrace(terminal.writer());
        }
    }
}
