package com.lucifaer.jokerframework.joker.core.factory;

import com.lucifaer.jokerframework.sdk.model.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/3
 */
public abstract class BaseFactory<T extends Model> {
    private Map<String, T> existMap = new HashMap<>();

    public Map<String, T> getExistMap() {
        return existMap;
    }

    public void setExistMap(Map<String, T> existMap) {
        this.existMap = existMap;
    }
}
