package com.lucifaer.jokerframework.joker.core.config;

import com.lucifaer.jokerframework.joker.core.shell.ShellHelper;
import com.lucifaer.jokerframework.joker.core.shell.ShellThrowableHandler;
import com.lucifaer.jokerframework.sdk.context.ShellContext;
import org.jline.terminal.Terminal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/3
 */
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
    public ShellThrowableHandler shellThrowableHandler(@Lazy Terminal terminal) {
        return new ShellThrowableHandler(terminal);
    }
}
