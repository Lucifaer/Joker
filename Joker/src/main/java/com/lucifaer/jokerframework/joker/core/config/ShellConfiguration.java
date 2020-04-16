package com.lucifaer.jokerframework.joker.core.config;

import com.lucifaer.jokerframework.sdk.context.ShellContext;
import com.lucifaer.jokerframework.joker.core.shell.ShellHelper;
import com.lucifaer.jokerframework.joker.core.shell.ShellThrowableHandler;
import org.jline.terminal.Terminal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

/**
 * ShellConfiguration类注册用于交互式shell的相关bean及配置
 * @author Lucifaer
 * @version 3.0
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
