package com.lucifaer.jokerframework.joker.core.shell.command;

import com.lucifaer.jokerframework.joker.core.context.JokerContext;
import com.lucifaer.jokerframework.joker.core.shell.ShellHelper;
import com.lucifaer.jokerframework.joker.core.shell.ShellThrowableHandler;
import com.lucifaer.jokerframework.sdk.context.ShellContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.standard.commands.Help;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/5
 */
@ShellComponent
public class HelpCommand implements Help.Command {
    private final JokerContext jokerContext;
    private final ShellHelper shellHelper;
    private final ShellThrowableHandler shellThrowableHandler;

    public HelpCommand(JokerContext jokerContext, ShellHelper shellHelper, ShellThrowableHandler shellThrowableHandler) {
        this.jokerContext = jokerContext;
        this.shellHelper = shellHelper;
        this.shellThrowableHandler = shellThrowableHandler;
    }

    @ShellMethod(value = "show joker Option helps", key = "help", group = "Common")
    public void help(@ShellOption(defaultValue = " ") String jokerOption) {
        String[] help = {};
        ShellContext currentContext = null;
        try {
            currentContext = (ShellContext) jokerContext.getCurrentShell();
        } catch (Exception e) {
            shellThrowableHandler.handleThrowable(e);
        }

        switch (jokerOption) {
            case "exp":
                help = new String[] {
                        "exp [exploitContextName]",
                        "   exploitContextName                  the only flag to mark specific context",
                };
                break;
            case "list":
                help = new String[] {
                        "list [type]",
                        "type has these option: ",
                        "   - session                           list exist context session",
                        "   - exploit                           list exist exploit plugins",
                };
                break;
            default:
                assert currentContext != null;
                if ("exploit".equals(currentContext.getContextType())) {
                    help = new String[] {
                            "exp's sub command: ",
                            "   show_options                        show exploit configurations",
                            "   set [configuration] [value]         set exploit configuration's value",
                            "   exploit [exp_name]                  run exploit",
                    };
                }
                else {
                    help = new String[] {
                            "exp [exploitContextName]               create an exploitContext to use exploit plugin",
                            "list [type]                            list exist type info",
                            "clear                                  clear the shell screen",
                            "exit                                   exit joker",
                            "help [command]                         show command help",
                    };
                }
        }
        for (String h : help) {
            shellHelper.echoDocument(h);
        }
    }
}
