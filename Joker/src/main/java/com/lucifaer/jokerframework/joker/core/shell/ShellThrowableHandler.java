package com.lucifaer.jokerframework.joker.core.shell;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.util.StringUtils;

/**
 * ShellThrowableHandler用于接收自定义错误，并以SpringShell的报错方式显示在交互式命令行中
 * @author Lucifaer
 * @version 3.0
 */
public class ShellThrowableHandler {
    private Terminal terminal;
    private Throwable lastError;

    public ShellThrowableHandler(Terminal terminal) {
        this.terminal = terminal;
    }

    /**
     * handleThrowable方法用于将自定义错误以SpringShell的报错方式显示在交互式命令行中
     * @param result 接收到的错误
     * @author Lucifaer
     * @version 3.0
    */
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

    /**
     * getLastError方法返回上一个错误
     * @return java.lang.Throwable 上一个错误
     * @author Lucifaer
     * @version 3.0
    */
    public Throwable getLastError() {
        return lastError;
    }
}
