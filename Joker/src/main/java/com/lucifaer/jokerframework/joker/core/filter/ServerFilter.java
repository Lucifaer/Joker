package com.lucifaer.jokerframework.joker.core.filter;

import com.lucifaer.jokerframework.joker.core.context.JokerContext;
import com.lucifaer.jokerframework.sdk.context.ShellContext;
import com.lucifaer.jokerframework.joker.core.error.core.ParamLost;
import com.lucifaer.jokerframework.joker.core.error.server.PortAlreadyUsed;
import com.lucifaer.jokerframework.joker.core.server.Server;
import com.lucifaer.jokerframework.joker.core.shell.ShellHelper;
import com.lucifaer.jokerframework.joker.core.shell.ShellThrowableHandler;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ServerFilter为Server的参数过滤器，用于校验Server的参数是否符合要求，同时完成Server的一些参数预处理工作
 * @author Lucifaer
 * @version 3.0
 */
@Component
public class ServerFilter extends BaseFilter<Server> {
    private final ShellHelper shellHelper;
    private final JokerContext jokerContext;

    private final ShellThrowableHandler shellThrowableHandler;

    public ServerFilter(ShellHelper shellHelper, JokerContext jokerContext, ShellThrowableHandler shellThrowableHandler) {
        this.shellHelper = shellHelper;
        this.jokerContext = jokerContext;
        this.shellThrowableHandler = shellThrowableHandler;
    }

    @Override
    public void preCheck(Server server, ShellContext shellContext) {
        List<String> lostParams = new ArrayList<>();
        Map<String, String> params = shellContext.getParams();
//      校验shellcontext中是否包含了用户自定义的Url，如果设定了Url且不为空，则将用户自定的URL设置到对应的server上
        if (params.containsKey("serverUrl") && params.get("serverUrl") != null) {
            server.setServerUrl(params.get("serverUrl"));
        }
//      校验shellcontext中是否包含了用户自定义的Port，如果设定了Port且不为空，则将用户自定的Port设置到对应的server上，否则将使用默认的Port
        if (params.containsKey("serverPort") && params.get("serverPort") != null) {
            server.setServerPort(params.get("serverPort"));
        } else {
            params.put("serverPort", server.getServerPort());
        }
//      校验shellcontext中是否包含Server必须填写的参数
        for (String p : server.getRequiredParams()) {
            if (!params.containsKey(p)) {
                lostParams.add(p);
            }
            else if (params.get(p) == null) {
                lostParams.add(p);
            }
        }
        if (lostParams.isEmpty()) {
//          校验端口是否已经占用
            if (!jokerContext.getServerMap().values().isEmpty()) {
                try {
                    for (Map serverInfo : jokerContext.getServerMap().values()) {
                        if (serverInfo.get("serverPort") == params.get("serverPort")) {
                            shellHelper.echoError(serverInfo.get("serverName") + " has already use port " + serverInfo.get("serverPort"));
                            throw new PortAlreadyUsed(serverInfo);
                        }
                    }
                } catch (PortAlreadyUsed portAlreadyUsed) {
                    shellThrowableHandler.handleThrowable(portAlreadyUsed);
                }
            }
//          校验必须设置的值中remote_factoryLocation是否符合URL规范
            try {
                URL remoteFactoryLocation = new URL(params.get("remote_factoryLocation"));
            } catch (MalformedURLException e) {
                shellHelper.echoError("remote_factoryLocation's Url error.You should type remote_factoryLocation with http:// or https://");
                shellThrowableHandler.handleThrowable(e);
            }
        }
        else {
            try {
                for (String p : lostParams) {
                    shellHelper.echoError("Param " + p + " must be confirmed!");
                }
                throw new ParamLost(lostParams);
            } catch (ParamLost paramLost) {
                shellThrowableHandler.handleThrowable(paramLost);
            }
        }
    }
}
