package com.lucifaer.jokerframework.core.config;

import com.lucifaer.jokerframework.data.core.ShellDataModel;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Configuration
public class ShellConfiguration {
    @Bean(value = "shellContext")
    public ShellDataModel shellContext() {
        ShellDataModel shellDataModel = new ShellDataModel();
        Map<String, Object> shellContext = shellDataModel.getShellContext();
        shellContext.put("defaultAttributedString", "Joker$ ");
        shellContext.put("currentAttributedString", "");
        shellContext.put("defaultCommandNode", "root");
        shellContext.put("currentCommandNode", "");
        shellContext.put("preCommandNode", new Stack<String>());
        shellContext.put("params", new HashMap<String, String>());
        return shellDataModel;
    }

    @Bean
    @Qualifier("shellContext")
    public PromptProvider jokerPromptProvider(ShellDataModel shellDataModel) {
        if (shellDataModel.getShellContextValue("currentAttributedString").toString().isEmpty()) {
            shellDataModel.setShellContext("currentAttributedString", shellDataModel.getShellContextValue("defaultAttributedString"));
        }

        if (shellDataModel.getShellContextValue("currentCommandNode").toString().isEmpty()) {
            shellDataModel.setShellContext("currentCommandNode", shellDataModel.getShellContextValue("defaultCommandNode"));
        }
        return () -> new AttributedString((CharSequence) shellDataModel.getShellContextValue("currentAttributedString"), AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }
}
