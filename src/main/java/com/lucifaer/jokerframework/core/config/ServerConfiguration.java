package com.lucifaer.jokerframework.core.config;

import com.lucifaer.jokerframework.core.factory.ServerFactory;
import com.lucifaer.jokerframework.data.JokerContext;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(JokerContext.class)
public class ServerConfiguration {
    @Bean
    public ServerFactory serverFactory() {
        return new ServerFactory();
    }
}
