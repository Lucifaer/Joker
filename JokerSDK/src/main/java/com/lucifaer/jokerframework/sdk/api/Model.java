package com.lucifaer.jokerframework.sdk.api;

import java.util.List;

/**
 * @author Lucifaer
 * @version 3.0
 */
public interface Model {
    /**
     * getModelTypeName方法用于获取Model的类型，如server、exploit
     * @return java.lang.String Model的类型
     * @author Lucifaer
     * @version 3.0
     */
    String getModelTypeName();

    /**
     * getName方法用于获取具体的model名称，如rmi-server、ldap-server
     * @return java.lang.String 具体model的名称
     * @author Lucifaer
     * @version 3.0
     */
    String getName();

    /**
     * getRequiredParams方法用于获取Model定义的必须设置值的参数
     * @return java.util.List Model必须设置值的参数
     * @author Lucifaer
     * @version 3.0
     */
    List<String> getRequiredParams();
}
