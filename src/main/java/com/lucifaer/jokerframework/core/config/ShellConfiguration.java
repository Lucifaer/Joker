package com.lucifaer.jokerframework.core.config;

import com.lucifaer.jokerframework.core.context.ShellContext;
import com.lucifaer.jokerframework.core.shell.ShellHelper;
import com.lucifaer.jokerframework.core.shell.ShellThrowableHandler;
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
    public ShellThrowableHandler shellThrowableHandler(@Lazy Terminal terminal) {return new ShellThrowableHandler(terminal);}
}
