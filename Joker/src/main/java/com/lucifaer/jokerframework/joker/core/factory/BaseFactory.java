package com.lucifaer.jokerframework.joker.core.factory;

import com.lucifaer.jokerframework.sdk.api.Model;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * BaseFactory抽象类用于扫描所有实现了BaseModel接口的bean，并将某一类型的bean全部注册在existMap中。
 * @author Lucifaer
 * @version 3.0
 */
public abstract class BaseFactory<T extends Model> implements ApplicationContextAware {
    private Class beansOfType = null;
    private Map<String, T> existMap = new HashMap<>();

    /**
     * getBeansOfType方法用于获取将扫描并注册bean的类型
     * @return Class bean的类型
     * @author Lucifaer
     * @version 3.0
    */
    public Class getBeansOfType() {
        return beansOfType;
    }

    /**
     * setBeansOfType方法用于设置将扫描并注册的bean的类型
     * @param beansOfType 将扫描并注册的bean的类型
     * @author Lucifaer
     * @version 3.0
    */
    public void setBeansOfType(Class beansOfType) {
        this.beansOfType = beansOfType;
    }

    /**
     * getExistMap方法用于获取已经注册的beanMap
     * @return java.util.Map 已经注册的beanMap
     * @author Lucifaer
     * @version 3.0
    */
    public Map<String, T> getExistMap() {
        return existMap;
    }

    /**
     * setExistMap方法用于设置已经注册的beanMap
     * @param existMap 将要设置的beanMap
     * @return void
     * @author Lucifaer
     * @version 3.0
    */
    public void setExistMap(Map<String, T> existMap) {
        this.existMap = existMap;
    }

    /**
     * setApplicationContext方法重写了ApplicationContextAware#setApplication方法，根据已经设置的beansOfType来扫描ApplicationContext中所有符合条件的bean，并将其注册到existMap中
     * @param applicationContext SpringBoot的ApplicationContext，自动注入
     * @author Lucifaer
     * @version 3.0
    */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (null == this.beansOfType) {
            throw new RuntimeException("Not specific a beans of type");
        }
        Map<String, T> beansMap = applicationContext.getBeansOfType(beansOfType);
        for (Map.Entry<String, T> entry : beansMap.entrySet()) {
            if (StringUtils.isEmpty(entry.getKey())) {
                throw new RuntimeException("Name not found: " + entry.getKey());
            }
            if (existMap.get(entry.getKey()) != null) {
                throw new RuntimeException(entry.getKey() + " already used by " + existMap.get(entry.getKey()));
            }
            existMap.put(entry.getValue().getName(), entry.getValue());
        }
    }
}
