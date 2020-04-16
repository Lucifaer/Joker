package com.lucifaer.jokerframework.joker.core.server;

import com.lucifaer.jokerframework.sdk.context.ShellContext;
import com.lucifaer.jokerframework.sdk.api.Model;
import org.springframework.scheduling.annotation.Async;

/**
 * Server接口负责对外提供Server相关方法，包括启动服务和关闭服务
 * @author Lucifaer
 * @version 3.0
 */
public interface Server extends Model {
    /**
     * createServer方法用于异步启动服务
     * @param shellContext server Type的Context，其中包含Server相关配置
     * @author Lucifaer
     * @version 3.0
    */
    @Async("serverExecutor")
    void createServer(ShellContext shellContext);

    /**
     * stopServer方法用于关闭相关服务
     * @author Lucifaer
     * @version 3.0
    */
    void stopServer();

    /**
     * getServerName方法用于获取服务名称
     * @return java.lang.String 服务名称
     * @author Lucifaer
     * @version 3.0
    */
    String getServerName();
    
    /**
     * getServerPort方法用于获取服务端口
     * @return java.lang.String 服务端口
     * @author Lucifaer
     * @version 3.0
    */
    String getServerPort();
    
    /**
     * getRequiredParams方法用于获取必须设置的参数
     * @return java.util.Map<java.lang.String,java.lang.String>
     * @author Lucifaer
     * @version 3.0
    */
//    Map<String, String>getRequiredParams();

    /**
     * setServerUrl方法用于设置Server开放的url
     * @param serverUrl 开放服务的url
     * @author Lucifaer
     * @version 3.0
    */
    void setServerUrl(String serverUrl);
    
    /**
     * setServerPort方法用于设置Server开放的端口
     * @param serverPort 开放服务的端口
     * @author Lucifaer
     * @version 3.0
    */
    void setServerPort(String serverPort);
}
