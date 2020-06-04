package com.lucifaer.jokerframework.joker.core.shell;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/3
 */
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

    /**
     * getColored方法用于设置交互式命令行的显示颜色
     * @param message 输出信息
     * @param color 显示颜色
     * @return java.lang.String 返回设置颜色的文本信息
     * @author Lucifaer
     */
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

    /**
     * echo方法用于直接输出具有颜色的相关信息
     * @param message 输出的信息
     * @param color 显示颜色
     * @author Lucifaer
     */
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
        echo(message, PromptColor.valueOf(infoColor));
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
