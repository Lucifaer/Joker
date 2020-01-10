package com.lucifaer.jokerframework.core.shell.config;

import com.lucifaer.jokerframework.data.JokerContext;
import com.lucifaer.jokerframework.data.ShellContext;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class JokerPromptProvider implements PromptProvider {
    @Autowired
    JokerContext jokerContext;
    private ShellContext shellContext;

    @Override
    public AttributedString getPrompt() {
        String prompt = "Joker:>";
        String currentShellContextName = jokerContext.getCurrentShell();
        if (currentShellContextName != null) {
            shellContext = jokerContext.getCurrentShellContext();
            for (String node : shellContext.commandNode) {
                if ("use".equals(node) || "server".equals(node)) {
                    try {
                        prompt = String.format("(%s) Joker:>", shellContext.contextName);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }
        return new AttributedString(prompt, AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE));
    }
}
