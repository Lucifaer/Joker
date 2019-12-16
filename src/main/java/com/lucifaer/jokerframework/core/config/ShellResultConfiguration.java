package com.lucifaer.jokerframework.core.config;

import com.lucifaer.jokerframework.utils.ShellResultHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.ResultHandler;

@Configuration
public class ShellResultConfiguration implements ResultHandler<ShellResultHandler> {
    @Override
    public void handleResult(ShellResultHandler result) {
    }
}
