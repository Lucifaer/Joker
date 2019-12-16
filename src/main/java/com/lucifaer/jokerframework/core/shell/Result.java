package com.lucifaer.jokerframework.core.shell;

public class Result {
    private final String result;
    private final String option;
    private final String currentCommand;

    public Result(String result, String option, String currentCommand) {
        this.result = result;
        this.option = option;
        this.currentCommand = currentCommand;
    }

    public String getResult() {
        return result;
    }

    public String getOption() {
        return option;
    }

    public String getCurrentCommand() {
        return currentCommand;
    }
}
