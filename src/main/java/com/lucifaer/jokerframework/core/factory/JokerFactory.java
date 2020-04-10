package com.lucifaer.jokerframework.core.factory;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public abstract class JokerFactory<T> implements ApplicationContextAware {
    private Class beansOfType = null;
    private Map<String, T> existMap = new HashMap<>();

    public Class getBeansOfType() {
        return beansOfType;
    }

    public void setBeansOfType(Class beansOfType) {
        this.beansOfType = beansOfType;
    }

    public Map<String, T> getExistMap() {
        return existMap;
    }

    public void setExistMap(Map<String, T> existMap) {
        this.existMap = existMap;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (null == this.beansOfType) {
            throw new RuntimeException("Not specific a beans of type");
        }
        Map<String, T> map = applicationContext.getBeansOfType(beansOfType);
        for (Map.Entry<String, T> entry : map.entrySet()) {
            if (StringUtils.isEmpty(entry.getKey())) {
                throw new RuntimeException("Name not found: " + entry.getValue());
            }
            if (existMap.get(entry.getKey()) != null) {
                throw new RuntimeException(entry.getKey() + " already used by "
                        + existMap.get(entry.getKey()));
            }
            existMap.put(entry.getKey(), entry.getValue());
        }
    }
}
