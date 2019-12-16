package com.lucifaer.jokerframework.core.shell;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.shell.jline.PromptProvider;

import static com.lucifaer.jokerframework.data.JokerShellDataModel.BaseJokerShellDataModel.*;

@Configurable
@Import(ResultConfiguration.class)
public class JokerShellConfiguration {
    @Bean
    public PromptProvider JokerPromptProvider() {
        if (currentAttributedString.isEmpty()) {
            currentAttributedString = defaultAttributedString;
        }

        if (currentCommandNode.isEmpty()) {
            currentCommandNode = defaultCommandNode;
        }
        return () -> new AttributedString(currentAttributedString, AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }


}
