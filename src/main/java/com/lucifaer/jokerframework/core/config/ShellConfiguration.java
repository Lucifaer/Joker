package com.lucifaer.jokerframework.core.config;

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
import java.util.Map;
import java.util.Stack;

import static com.lucifaer.jokerframework.utils.Output.echo;

@Configuration
public class ShellConfiguration implements ResultHandler<ShellResultHandler> {
    @Bean(value = "shellContext")
    public ShellDataModel shellContext() {
        Map<String, Object> shellContext = new HashMap<>();
        shellContext.put("defaultAttributedString", "Joker$ ");
        shellContext.put("currentAttributedString", "");
        shellContext.put("defaultCommandNode", "root");
        shellContext.put("currentCommandNode", "");
        shellContext.put("preCommandNode", new Stack<String>());
        shellContext.put("params", new HashMap<String, String>());
        ShellDataModel shellDataModel = new ShellDataModel(shellContext);
        DataModel.setJokerContext("shellContext", shellDataModel);
        return shellDataModel;
    }

    @Bean
    @Qualifier("shellContext")
    public PromptProvider jokerPromptProvider(ShellDataModel shellDataModel) {
        if (shellDataModel.getShellContextValue("currentAttributedString").toString().isEmpty()) {
            shellDataModel.setShellContextValue("currentAttributedString", shellDataModel.getShellContextValue("defaultAttributedString"));
        }

        if (shellDataModel.getShellContextValue("currentCommandNode").toString().isEmpty()) {
            shellDataModel.setShellContextValue("currentCommandNode", shellDataModel.getShellContextValue("defaultCommandNode"));
        }
        return () -> new AttributedString((CharSequence) shellDataModel.getShellContextValue("currentAttributedString"), AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }

    @Override
    public void handleResult(ShellResultHandler result) {
        String defaultAttributedString = (String) shellContext().getShellContextValue("defaultAttributedString");
        if ("use".equals(result.getCurrentCommand())) {
            shellContext().getShellContext().replace("currentAttributedString", String.format("(%s)", result.getValue()) + defaultAttributedString);
        }

        if ("set".equals(result.getCurrentCommand())) {
            echo(String.format("set %s: %s", result.getOption(), result.getValue()));
        }
    }
}
