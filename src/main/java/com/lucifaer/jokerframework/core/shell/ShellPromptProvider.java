package com.lucifaer.jokerframework.core.shell;

import com.lucifaer.jokerframework.core.context.Context;
import com.lucifaer.jokerframework.core.context.JokerContext;
import com.lucifaer.jokerframework.core.context.ShellContext;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class ShellPromptProvider implements PromptProvider {
    @Autowired
    JokerContext jokerContext;

    private ShellContext shellContext;

    @Override
    public AttributedString getPrompt() {
        String prompt = "Joker:>";
        Context currentShellContext = jokerContext.currentShell;
        if (currentShellContext != null){
            shellContext = (ShellContext) currentShellContext;
            for (String node : shellContext.commandNode) {
                if ("exp".equals(node) || "server".equals(node)) {
                    try {
                        prompt = String.format("(%s) Joker:>", shellContext.getContextName());
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
