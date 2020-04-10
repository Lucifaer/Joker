package com.lucifaer.jokerframework.core.exception;

public class ServerContextNameAlreadyUsed extends JokerException {
    public ServerContextNameAlreadyUsed(String processId) {
        this.message = "Already has an server context named " + processId + ". Please change another name.";
    }
}
