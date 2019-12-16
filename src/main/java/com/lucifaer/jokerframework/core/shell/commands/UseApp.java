package com.lucifaer.jokerframework.core.shell.commands;

import com.lucifaer.jokerframework.core.WiringBeans;
import com.lucifaer.jokerframework.core.mode.ExploitMode;
import com.lucifaer.jokerframework.core.shell.Result;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import static com.lucifaer.jokerframework.core.shell.commands.ShowApp.defaultExploitConfigurations;
import static com.lucifaer.jokerframework.core.shell.commands.ShowApp.defaultPayloadConfigurations;
import static com.lucifaer.jokerframework.data.JokerShellDataModel.BaseJokerShellDataModel.*;
import static com.lucifaer.jokerframework.utils.OutPut.echo;

@ShellComponent
public class UseApp {
    private boolean used = false;
    @ShellMethod(value = "use exploit Model", key = "use", group = "Joker")
    public Result doUse(String exploitName) {
        params.put("exploitName", exploitName);
        preCommandNode.push(currentCommandNode);
        currentCommandNode = "exploit";
        used = true;
        return new Result(exploitName, "", "use");
    }

    @ShellMethod(value = "set options", key = "set", group = "Joker")
    public Result doSet(String config, String value) {
        params.put(config, value);
        preCommandNode.push(currentCommandNode);
        if ("payloadName".equals(config)) {
            currentCommandNode = "payload";
        }
        else {
            currentCommandNode = "set";
        }
        return new Result(value, config, "set");
    }

    @ShellMethod(value = "show configurations", key = "show_options", group = "Joker")
    public void show() {
        if ("exploit".equals(currentCommandNode)) {
            for (String s : defaultExploitConfigurations()) {
                echo(s);
            }
        }
        else if ("payload".equals(currentCommandNode)) {
            for (String s : defaultExploitConfigurations()) {
                echo(s);
            }

            for (String s : defaultPayloadConfigurations()) {
                echo(s);
            }
        }
        else if ("set".equals(currentCommandNode) && ("set".equals(preCommandNode.peek()) || "payload".equals(preCommandNode.peek()))) {
            for (String s : defaultExploitConfigurations()) {
                echo(s);
            }

            for (String s : defaultPayloadConfigurations()) {
                echo(s);
            }
        }
        else {
            echo("you should set payload first");
        }
    }

    @ShellMethod(value = "run exploitation", key = "exploit", group = "Joker")
    public void exploit() throws Exception {
        params.put("installUrl", "http://127.0.0.1:9999");
        ExploitMode.initModel(params);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WiringBeans.class);
        context.getBean("execute");
    }

    @ShellMethodAvailability({"set", "show_options"})
    public Availability useCheck() {
        return used ? Availability.available() : Availability.unavailable("you should use an exploit first");
    }

}
