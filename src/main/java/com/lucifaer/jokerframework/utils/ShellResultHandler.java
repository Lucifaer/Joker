package com.lucifaer.jokerframework.utils;

public class ShellResultHandler {
    private final String currentCommand;

    public ShellResultHandler(String currentCommand) {
        this.currentCommand = currentCommand;
    }

    public String getCurrentCommand() {
        return currentCommand;
    }
}
