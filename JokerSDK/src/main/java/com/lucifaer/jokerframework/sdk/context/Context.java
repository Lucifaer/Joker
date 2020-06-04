package com.lucifaer.jokerframework.sdk.context;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/3
 */
public interface Context {
    String getContextName();

    void setContextName(String contextName);

    String getContextType();
}
