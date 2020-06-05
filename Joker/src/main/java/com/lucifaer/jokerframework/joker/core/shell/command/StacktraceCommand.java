package com.lucifaer.jokerframework.joker.core.shell.command;

import com.lucifaer.jokerframework.joker.core.shell.ShellThrowableHandler;
import org.jline.terminal.Terminal;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Stacktrace;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/4
 */
@ShellComponent
public class StacktraceCommand implements Stacktrace.Command {
    private final Terminal terminal;
    private final ShellThrowableHandler shellThrowableHandler;

    public StacktraceCommand(@Lazy Terminal terminal, ShellThrowableHandler shellThrowableHandler) {
        this.terminal = terminal;
        this.shellThrowableHandler = shellThrowableHandler;
    }

    @ShellMethod(value = "Display the full stacktrace of the last error.", key = "stacktrace")
    public void stacktrace() {
        if (shellThrowableHandler.getLastError() != null) {
            shellThrowableHandler.getLastError().printStackTrace(terminal.writer());
        }
    }
}
