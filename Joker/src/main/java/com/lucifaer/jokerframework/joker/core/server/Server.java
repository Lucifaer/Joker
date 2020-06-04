package com.lucifaer.jokerframework.joker.core.server;

import com.lucifaer.jokerframework.sdk.context.ShellContext;
import org.springframework.scheduling.annotation.Async;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/3
 */
public interface Server {
    /**
     * createServer方法用于异步启动服务
     * @param shellContext server Type的Context，其中包含Server相关配置
     * @author Lucifaer
     */
    @Async("serverExecutor")
    void createServer(ShellContext shellContext);

    /**
     * stopServer方法用于关闭相关服务
     * @author Lucifaer
     */
    void stopServer();

    /**
     * getServerName方法用于获取服务名称
     * @return java.lang.String 服务名称
     * @author Lucifaer
     */
    String getServerName();

    /**
     * getServerPort方法用于获取服务端口
     * @return java.lang.String 服务端口
     * @author Lucifaer
     */
    String getServerPort();

    /**
     * getRequiredParams方法用于获取必须设置的参数
     * @return java.util.Map<java.lang.String,java.lang.String>
     * @author Lucifaer
     */
//    Map<String, String>getRequiredParams();

    /**
     * setServerUrl方法用于设置Server开放的url
     * @param serverUrl 开放服务的url
     * @author Lucifaer
     */
    void setServerUrl(String serverUrl);

    /**
     * setServerPort方法用于设置Server开放的端口
     * @param serverPort 开放服务的端口
     * @author Lucifaer
     */
    void setServerPort(String serverPort);
}
