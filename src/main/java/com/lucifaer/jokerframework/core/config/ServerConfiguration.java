package com.lucifaer.jokerframework.core.config;

import com.lucifaer.jokerframework.core.factory.ServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfiguration {
    @Bean
    public ServerFactory serverFactory() {
        return new ServerFactory();
    }
}
