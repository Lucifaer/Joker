package com.lucifaer.jokerframework;

import com.lucifaer.jokerframework.core.config.JokerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(JokerConfiguration.class)
public class Joker {
    public static void main(String[] args) {
        SpringApplication.run(Joker.class, args);
    }
}
