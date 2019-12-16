package com.lucifaer.jokerframework.core.shell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(JokerShellConfiguration.class)
public class JokerShell {
    public static void main(String[] args) {
        SpringApplication.run(JokerShell.class, args);
    }
}
