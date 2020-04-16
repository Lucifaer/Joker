package com.lucifaer.jokerframework.joker.core.server;

import com.lucifaer.jokerframework.joker.core.BaseModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Lucifaer
 * @version 3.0
 */
@Component
public class ServerModel extends BaseModel<Server> {
    @Override
    public String getModelTypeName() {
        return "server";
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public List<String> getRequiredParams() {
        return null;
    }
}
