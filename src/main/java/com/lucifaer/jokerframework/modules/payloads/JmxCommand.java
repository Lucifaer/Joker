package com.lucifaer.jokerframework.modules.payloads;

import com.lucifaer.jokerframework.modules.Payload;

public class JmxCommand implements Payload {
    private String test;
    @Override
    public void exec() {
        System.out.println(this.test);
    }

    @Override
    public void init(Object object) {
        this.test = (String) object;
    }
}
