package com.lucifaer.jokerframework.core.shell.commands;

import com.lucifaer.jokerframework.data.core.ShellDataModel;
import com.lucifaer.jokerframework.utils.ShellResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Stack;

import static com.lucifaer.jokerframework.utils.Output.echo;

@ShellComponent
public class UseApp {
    @Autowired
    private ShellDataModel shellDataModel;

    @Autowired
    private ShowApp showApp;

    private boolean used = false;

    @ShellMethod(value = "use exploit Model", key = "use", group = "Joker")
    public ShellResultHandler doUse(String exploitName) {
        shellDataModel.getParams().put("exploitName", exploitName);
        shellDataModel.getPreCommandNode().push(shellDataModel.getCurrentCommandNode());
        shellDataModel.setCurrentCommandNode("exploit");
        used = true;
        return new ShellResultHandler(exploitName, "use");
    }

    @ShellMethod(value = "set options", key = "set", group = "Joker")
    public ShellResultHandler doSet(String config, String value) {
        shellDataModel.getParams().put(config, value);
        shellDataModel.getPreCommandNode().push(shellDataModel.getCurrentCommandNode());
        if ("payloadName".equals(config)) {
            shellDataModel.setCurrentCommandNode("payload");
        }
        else {
            shellDataModel.setCurrentCommandNode("set");
        }
        return new ShellResultHandler(config, value, "set");
    }

    @ShellMethod(value = "show configurations", key = "show_options", group = "Joker")
    public void show() {
        String currentCommandNode = shellDataModel.getCurrentCommandNode();
        Stack preCommandNode = shellDataModel.getPreCommandNode();
        if ("exploit".equals(currentCommandNode)) {
            for (String s : showApp.defaultExploitConfigurations()) {
                echo(s);
            }
        }
        else if ("payload".equals(currentCommandNode)) {
            for (String s : showApp.defaultExploitConfigurations()) {
                echo(s);
            }

            for (String s : showApp.defaultPayloadConfigurations()) {
                echo(s);
            }
        }
        else if ("set".equals(currentCommandNode) && ("set".equals(preCommandNode.peek()) || "payload".equals(preCommandNode.peek()))) {
            for (String s : showApp.defaultExploitConfigurations()) {
                echo(s);
            }

            for (String s : showApp.defaultPayloadConfigurations()) {
                echo(s);
            }
        }
        else {
            echo("you should set payload first");
        }
    }
}
