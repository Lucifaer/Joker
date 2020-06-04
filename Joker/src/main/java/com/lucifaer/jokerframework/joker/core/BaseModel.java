package com.lucifaer.jokerframework.joker.core;

import com.lucifaer.jokerframework.sdk.model.Model;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/3
 */
public abstract class BaseModel<T> implements Model {
    @Override
    public String getModelType() {
        return null;
    }

    @Override
    public String getModelName() {
        return null;
    }
}
