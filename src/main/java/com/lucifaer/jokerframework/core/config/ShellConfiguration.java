package com.lucifaer.jokerframework.core.config;

import com.lucifaer.jokerframework.core.shell.commands.ShowApp;
import com.lucifaer.jokerframework.data.DataModel;
import com.lucifaer.jokerframework.data.core.ShellDataModel;
import com.lucifaer.jokerframework.utils.ShellResultHandler;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.ResultHandler;
import org.springframework.shell.jline.PromptProvider;

import java.util.HashMap;
import java.util.Stack;

import static com.lucifaer.jokerframework.utils.Output.echo;

@Configuration
public class ShellConfiguration implements ResultHandler<ShellResultHandler> {
    @Bean(value = "shellContext")
    public ShellDataModel shellContext() {
        ShellDataModel shellDataModel = new ShellDataModel();
        shellDataModel.setDefaultAttributedString("Joker$ ");
        shellDataModel.setCurrentAttributedString("");
        shellDataModel.setDefaultCommandNode("root");
        shellDataModel.setCurrentCommandNode("");
        shellDataModel.setPreCommandNode(new Stack<>());
        shellDataModel.setParams(new HashMap<>());
        return shellDataModel;
    }

    @Bean
    @Qualifier("shellContext")
    public PromptProvider jokerPromptProvider(ShellDataModel shellDataModel) {
        if (shellDataModel.getCurrentAttributedString().isEmpty()) {
            shellDataModel.setCurrentAttributedString(shellDataModel.getDefaultAttributedString());
        }

        if (shellDataModel.getCurrentCommandNode().isEmpty()) {
            shellDataModel.setCurrentCommandNode(shellDataModel.getDefaultCommandNode());
        }
        return () -> new AttributedString(shellDataModel.getCurrentAttributedString(), AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }

    @Bean
    public ShowApp showOptions() {
        return new ShowApp();
    }

    @Override
    public void handleResult(ShellResultHandler result) {
        String defaultAttributedString = shellContext().getDefaultAttributedString();
        if ("use".equals(result.getCurrentCommand()) || "server".equals(result.getCurrentCommand())) {
            shellContext().setCurrentAttributedString(String.format("(%s)", result.getValue()) + defaultAttributedString);
        }

        if ("set".equals(result.getCurrentCommand())) {
            echo(String.format("set %s: %s", result.getOption(), result.getValue()));
        }
    }
}
