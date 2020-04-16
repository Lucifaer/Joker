package com.lucifaer.jokerframework.joker.core.shell;

import com.lucifaer.jokerframework.sdk.context.Context;
import com.lucifaer.jokerframework.joker.core.context.JokerContext;
import com.lucifaer.jokerframework.sdk.context.ShellContext;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

/**
 * ShellPromptProvider用于设置交互式命令行的显示前缀
 * @author Lucifaer
 * @version 3.0
 */
@Component
public class ShellPromptProvider implements PromptProvider {
    private final JokerContext jokerContext;

    public ShellPromptProvider(JokerContext jokerContext) {
        this.jokerContext = jokerContext;
    }

    /**
     * getPrompt方法用于设置交互式命令行的显示前缀，分为三种情况:
     * <p>1. "Joker":  默认情况下</p>
     * <p>2. "exp":    exploit情况下</p>
     * <p>3. "server": 启用服务情况下</p>
     * @return org.jline.utils.AttributedString 返回一个自定义的前缀对象
     * @author Lucifaer
     * @version 3.0
    */
    @Override
    public AttributedString getPrompt() {
        String prompt = "Joker:>";
        Context currentShellContext = jokerContext.currentShell;
        if (currentShellContext != null){
            ShellContext shellContext = (ShellContext) currentShellContext;
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
