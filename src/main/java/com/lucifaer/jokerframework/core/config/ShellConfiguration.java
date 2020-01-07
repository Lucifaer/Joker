package com.lucifaer.jokerframework.core.config;

import com.lucifaer.jokerframework.core.shell.config.DefaultDocumentation;
import com.lucifaer.jokerframework.core.shell.config.ShellHelper;
import com.lucifaer.jokerframework.data.ShellContext;
import org.jline.terminal.Terminal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

@Configuration
public class ShellConfiguration {
    @Bean
    @Scope("prototype")
    public ShellContext shellContext() {
        return new ShellContext();
    }

    @Bean
    public ShellHelper shellHelper(@Lazy Terminal terminal) {
        return new ShellHelper(terminal);
    }

    @Bean
    public DefaultDocumentation defaultDocumentation() {
        return new DefaultDocumentation();
    }
}
