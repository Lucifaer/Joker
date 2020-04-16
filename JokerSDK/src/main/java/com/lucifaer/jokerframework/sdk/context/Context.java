package com.lucifaer.jokerframework.sdk.context;

/**
 * Context接口负责对外提供Context的内容获取方法
 * @author Lucifaer
 * @version 3.0
 */
public interface Context {
    /**
     * 获取Context的名称，主要用于索引相应的上下文
     * @author Lucifaer
     * @return java.lang.String 返回Context名称
     * @version 3.0
    */
    String getContextName();

    /**
     * 设置Context的名称，主要用于Context的注册及测试
     * @param contextName 需要设置的ContextName
     * @author Lucifaer
     * @version 3.0
    */
    void setContextName(String contextName);

    /**
     * 获取Context的类型，用于校验Context是否符合需求
     * @return java.lang.String
     * @author Lucifaer
     * @return string 返回Context的类型
     * @version 3.0
    */
    String getContextType();
}
