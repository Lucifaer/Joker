package com.lucifaer.jokerframework.core.config;

import com.lucifaer.jokerframework.data.JokerContext;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Configurable
@Import(ShellConfiguration.class)
public class JokerConfiguration {
    @Bean
    public JokerContext jokerContext() {
        return new JokerContext();
    }
}
