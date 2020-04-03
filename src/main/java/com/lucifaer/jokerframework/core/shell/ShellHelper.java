package com.lucifaer.jokerframework.core.shell;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Value;

public class ShellHelper {
    @Value("${shell.out.info}")
    public String infoColor;

    @Value("${shell.out.success}")
    public String successColor;

    @Value("${shell.out.warning}")
    public String warningColor;

    @Value("${shell.out.error}")
    public String errorColor;

    @Value("${shell.out.document}")
    public String documentColor;

    private Terminal terminal;

    public ShellHelper(Terminal terminal) {
        this.terminal = terminal;
    }

    private String getColored(String message, PromptColor color) {
        return (new AttributedStringBuilder()).append(message, AttributedStyle.DEFAULT.foreground(color.toJlineAttributedStyle())).toAnsi();
    }

    public String getInfoMessage(String message) {
        return getColored(message, PromptColor.valueOf(infoColor));
    }

    public String getSuccessMessage(String message) {
        return getColored(message, PromptColor.valueOf(successColor));
    }

    public String getWarningMessage(String message) {
        return getColored(message, PromptColor.valueOf(warningColor));
    }

    public String getErrorMessage(String message) {
        return getColored(message, PromptColor.valueOf(errorColor));
    }


    public void echo(String message) {
        echo(message, null);
    }

    private void echo(String message, PromptColor color) {
        String toEcho = message;
        if (color != null) {
            toEcho = getColored(message, color);
        }
        terminal.writer().println(toEcho);
        terminal.flush();
    }

    public void echoSuccess(String message) {
        echo("[SUCCESS] " + message, PromptColor.valueOf(successColor));
    }

    public void echoInfo(String message) {
        echo("[INFO] " + message, PromptColor.valueOf(infoColor));
    }

    public void echoWarning(String message) {
        echo("[WARANING] " + message, PromptColor.valueOf(warningColor));
    }

    public void echoError(String message) {
        echo("[ERROR] " + message, PromptColor.valueOf(errorColor));
    }

    public void echoDocument(String message) {
        echo(message, PromptColor.valueOf(documentColor));
    }
}
