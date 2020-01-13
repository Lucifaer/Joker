package com.lucifaer.jokerframework.core.config;

import com.lucifaer.jokerframework.core.factory.JokerServerFactory;
import com.lucifaer.jokerframework.data.JokerContext;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(JokerContext.class)
public class ServerConfiguration {
    @Bean
    public JokerServerFactory serverFactory() {
        return new JokerServerFactory();
    }
}
