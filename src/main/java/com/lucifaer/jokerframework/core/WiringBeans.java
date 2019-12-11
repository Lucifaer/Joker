package com.lucifaer.jokerframework.core;

import com.lucifaer.jokerframework.core.factory.ExploitFactory;
import com.lucifaer.jokerframework.core.factory.PayloadFactory;
import com.lucifaer.jokerframework.modules.Exploit;
import com.lucifaer.jokerframework.modules.Payload;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WiringBeans {
    @Bean(name = "exploit")
    public Exploit getExploit() throws Exception {
        return (Exploit) new ExploitFactory().getObject();
    }

    @Bean(name = "payload")
    public Payload getPayload() throws Exception {
        return (Payload) new PayloadFactory().getObject();
    }

    @Bean(name = "execute")
    public String execute(Exploit exploit, Payload payload) {
        exploit.setPayload(payload);
        exploit.exploit();
        return "success";
    }
}
