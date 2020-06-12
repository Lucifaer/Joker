package com.lucifaer.jokerframework.sdk.console;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.util.List;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/11
 */
public abstract class DefaultParamsModel {
    @Parameter(names = "-u", description = "target url")
    private String targetUrl;
    @Parameter(names = "-p", description = "target port")
    private String targetPort;
    @Parameter(names = "-c", description = "command")
    private String command;

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getTargetPort() {
        return targetPort;
    }

    public void setTargetPort(String targetPort) {
        this.targetPort = targetPort;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
