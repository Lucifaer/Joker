package com.lucifaer.jokerframework.core.shell;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.util.StringUtils;

public class ShellThrowableHandler {
    private Terminal terminal;
    private Throwable lastError;

    public ShellThrowableHandler(Terminal terminal) {
        this.terminal = terminal;
    }

    public void handleThrowable(Throwable result) {
        lastError = result;
        String toPrint = StringUtils.hasLength(result.getMessage()) ? result.getMessage() : result.toString();
        terminal.writer().println(new AttributedString(toPrint,
                AttributedStyle.DEFAULT.foreground(AttributedStyle.RED)).toAnsi());
        terminal.flush();
        terminal.writer().println(
                new AttributedStringBuilder()
                        .append("Details of the error have been omitted. You can use the ", AttributedStyle.DEFAULT.foreground(AttributedStyle.RED))
                        .append("stacktrace", AttributedStyle.DEFAULT.foreground(AttributedStyle.RED).bold())
                        .append(" command to print the full stacktrace.", AttributedStyle.DEFAULT.foreground(AttributedStyle.RED))
                        .toAnsi()
        );
        terminal.flush();
    }

    public Throwable getLastError() {
        return lastError;
    }
}
