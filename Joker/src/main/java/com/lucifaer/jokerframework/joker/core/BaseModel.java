package com.lucifaer.jokerframework.joker.core;

import com.lucifaer.jokerframework.sdk.api.Model;
import org.springframework.stereotype.Component;

/**
 * @author Lucifaer
 * @version 3.0
 */
@Component
public abstract class BaseModel<T> implements Model {
    @Override
    public String getModelTypeName() {
        return null;
    }
}
