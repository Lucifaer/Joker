package com.lucifaer.jokerframework.joker;

import com.lucifaer.jokerframework.joker.core.util.JokerApplicationContextInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Joker类为JokerFramework的入口
 * @author Lucifaer
 * @version 3.0
*/
@SpringBootApplication
public class Joker {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Joker.class);
//        application.addInitializers(new JokerApplicationContextInitializer());
        application.run(args);
    }
}
