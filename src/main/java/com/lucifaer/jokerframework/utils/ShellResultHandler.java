package com.lucifaer.jokerframework.utils;

public class ShellResultHandler {
    private String value;
    private final String currentCommand;
    private String option;



    public ShellResultHandler(String prefix, String currentCommand) {
        this.value = prefix;
        this.currentCommand = currentCommand;
    }

    public ShellResultHandler(String option, String value, String currentCommand) {
        this.option = option;
        this.value = value;
        this.currentCommand = currentCommand;
    }

    public String getValue() {
        return value;
    }

    public String getOption() {
        return option;
    }

    public String getCurrentCommand() {
        return currentCommand;
    }
}
