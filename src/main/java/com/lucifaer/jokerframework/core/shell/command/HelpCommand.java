package com.lucifaer.jokerframework.core.shell.command;

import com.lucifaer.jokerframework.core.shell.config.ShellHelper;
import com.lucifaer.jokerframework.data.JokerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.standard.commands.Help;

@ShellComponent
public class HelpCommand implements Help.Command {
    @Autowired
    JokerContext jokerContext;

    @Autowired
    ShellHelper shellHelper;

    @ShellMethod(value = "show jokerOption helps", key = "help", group = "Joker")
    public void help(@ShellOption(defaultValue = " ") String jokerOption) {
        String[] help = {};
        String currentCommandNode = null;
        try {
            currentCommandNode = jokerContext.getCurrentShellContext().commandNode.peek();
        }catch (NullPointerException e) {

        }

        switch (jokerOption) {
            case "use":
                help = new String[]{
                        "use: ",
                        "   show_options            show exploit configurations",
                        "   set [configuration]     set exploit configuration's value",
                        "   exploit                 run exploit",
                };
                break;
            case "list":
                help = new String[]{
                        "list: ",
                        "   [type]                  witch type mods do you wanna list, eg: exploit",
                };
                break;
            default:
                if ("use".equals(currentCommandNode) || "set".equals(currentCommandNode)) {
                    help = new String[]{
                            "show_options: ",
                            "   show exploit configurations",
                            "set: ",
                            "   [configuration]     set exploit configuration's value",
                            "   exploit                 run exploit",
                    };
                }
                else {
                    help = new String[]{
                            "use: ",
                            "   [exploit_name]          which exploit you wanna use. eg: jmx",
                            "list: ",
                            "   [type]                  list type mod. eg: exploit mod or server mod",
                            "clear: ",
                            "   Clear the shell screen.",
                            "exit, quit: ",
                            "   Exit Joker",
                            "stacktrace: ",
                            "   Display the full stacktrace of the last error.",
                            "help: ",
                            "   [command]               show command help",
                    };
                }
        }

        for (String h : help) {
            shellHelper.echoDocument(h);
        }
    }
}
