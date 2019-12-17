package com.lucifaer.jokerframework.core.config;

import com.lucifaer.jokerframework.data.DataModel;
import com.lucifaer.jokerframework.data.core.ShellDataModel;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.shell.jline.PromptProvider;

import java.util.Map;

@Configurable
@Import(ShellConfiguration.class)
public class JokerConfiguration {
    @Bean(value = "jokerContext")
    public Map<String, Object> jokerContext(ShellDataModel shellDataModel) {
        DataModel.setJokerContext("shellContext", shellDataModel);
        return DataModel.getJokerContext();
    }
}
