package com.lucifaer.jokerframework.sdk.model;

import com.beust.jcommander.Parameter;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/11
 */
public class ConsoleBaseModel {
    @Parameter(names = "-u", description = "targetUrl")
    protected String targetUrl;
    @Parameter(names = "-p", description = "targetPort")
    protected String targetPort;
    @Parameter(names = "-c", description = "command")
    protected String command;

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
