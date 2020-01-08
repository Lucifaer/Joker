package com.lucifaer.jokerframework.core.config;

import com.lucifaer.jokerframework.data.JokerContext;
import com.lucifaer.jokerframework.data.ShellContext;
import com.lucifaer.jokerframework.modules.Exploit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import java.util.Map;

@Configurable
@Import(ShellConfiguration.class)
public class JokerConfiguration {
    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public JokerContext jokerContext() {
        return new JokerContext();
    }

    @Bean
    @Lazy
    public Exploit exploit(JokerContext jokerContext) throws Exception {
        Map<String, ? extends Exploit> map = applicationContext.getBeansOfType(Exploit.class);
        ShellContext shellContext = jokerContext.getCurrentShellContext();
        String paramsExploitName = shellContext.getParams().get("exploitName");
        for (Exploit exploit : map.values()) {
            if (paramsExploitName.equals(exploit.getExploitName())) {
                return exploit;
            }
        }

        throw new Exception("No exploit named " + paramsExploitName);
    }
}
