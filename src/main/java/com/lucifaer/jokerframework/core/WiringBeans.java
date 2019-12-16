package com.lucifaer.jokerframework.core;

import com.lucifaer.jokerframework.core.factory.ExploitFactory;
import com.lucifaer.jokerframework.core.factory.PayloadFactory;
import com.lucifaer.jokerframework.core.shell.JokerShellConfiguration;
import com.lucifaer.jokerframework.modules.Exploit;
import com.lucifaer.jokerframework.modules.Payload;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configurable
@Import(JokerShellConfiguration.class)
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
