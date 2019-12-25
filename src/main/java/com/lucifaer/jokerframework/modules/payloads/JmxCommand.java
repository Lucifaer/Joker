package com.lucifaer.jokerframework.modules.payloads;

import com.lucifaer.jokerframework.modules.Payload;

import java.util.Map;

public class JmxCommand implements Payload {
    private Map<String, String> params;

    @Override
    public void exec() {
        System.out.println(this.params.get("command"));
    }

    @Override
    public void init(Object object) {
        this.params = (Map<String, String>) object;
    }
}
