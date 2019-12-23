package com.lucifaer.jokerframework.core.shell.commands;

import com.lucifaer.jokerframework.data.core.ShellDataModel;
import com.lucifaer.jokerframework.utils.ShellResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Stack;

import static com.lucifaer.jokerframework.utils.Output.echo;

@ShellComponent
public class CommonApp {
    @Autowired
    private ShellDataModel shellContext;

    @Autowired
    private ShowApp showApp;

//  FIXME: 这里基于模版所设置的参数是有问题的，当server设置完后再次show的话会切换到exploit设置的参数展示上，同时在exploit参数设置完后再次利用use切换也会出现模版参数显示错误的问题
    @ShellMethod(value = "set options", key = "set", group = "Joker")
    public ShellResultHandler doSet(String config, String value) {
        shellContext.getParams().put(config, value);
        shellContext.getPreCommandNode().push(shellContext.getCurrentCommandNode());
        switch (config) {
            case "payloadName":
                shellContext.setCurrentCommandNode("payload");
                break;
            case "serverName":
                shellContext.setCurrentCommandNode("server");
                break;
            default:
                shellContext.setCurrentCommandNode("set");
        }
        return new ShellResultHandler(config, value, "set");
    }

    @ShellMethod(value = "show configurations", key = "show_options", group = "Joker")
    public void show() {
        String currentCommandNode = shellContext.getCurrentCommandNode();
        Stack preCommandNode = shellContext.getPreCommandNode();
        switch (currentCommandNode) {
            case "exploit":
                for (String s : showApp.defaultExploitConfigurations()) {
                    echo(s);
                }
                break;
            case "payload":
                for (String s : showApp.defaultExploitConfigurations()) {
                    echo(s);
                }
                for (String s : showApp.defaultPayloadConfigurations()) {
                    echo(s);
                }
                break;
            case "server":
                for (String s : showApp.defaultServerConfigurations()) {
                    echo(s);
                }
                break;
            case "set":
                if ("set".equals(preCommandNode.peek()) || "payload".equals(preCommandNode.peek())) {
                    for (String s : showApp.defaultExploitConfigurations()) {
                        echo(s);
                    }
                    for (String s : showApp.defaultPayloadConfigurations()) {
                        echo(s);
                    }
                }
                else if ("set".equals(preCommandNode.peek()) || "server".equals(preCommandNode.peek())) {
                    for (String s : showApp.defaultServerConfigurations()) {
                        echo(s);
                    }
                }
                break;
            default:
                echo("you should set payload first");

        }
    }
}
