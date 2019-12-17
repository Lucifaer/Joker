package com.lucifaer.jokerframework.core.shell.commands;

import com.lucifaer.jokerframework.data.DataModel;
import com.lucifaer.jokerframework.data.core.ShellDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.standard.commands.Help;

@ShellComponent
public class HelpApp implements Help.Command {
    @Autowired
    private ShellDataModel shellDataModel;
    @ShellMethod(value = "show jokerOption helps", key = "help", group = "Joker")
    public void help(@ShellOption(defaultValue = " ") String jokerOption) {
        String[] help = {};
        String currentCommandNode = (String) shellDataModel.getShellContextValue("currentCommandNode");
        switch (jokerOption) {
            case "use":
                help = new String[]{
                        "use: ",
                        "   show_options            show exploit configurations",
                        "   set [configuration]     set exploit configuration's value",
                };
                break;
            default:
                if (currentCommandNode.equals("exploit") || currentCommandNode.equals("payload") || currentCommandNode.equals("set")) {
                    help = new String[]{
                            "show_options: ",
                            "   show exploit configurations",
                            "set: ",
                            "   [configuration]     set exploit configuration's value"
                    };
                }
                else {
                    help = new String[]{
                            "use: ",
                            "   [exploit_name]          which exploit you wanna use. eg: JmxExploit",
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
            System.out.println(h);
        }
    }
}
