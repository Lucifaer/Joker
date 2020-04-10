package com.lucifaer.jokerframework.core.filter;

import com.lucifaer.jokerframework.core.context.JokerContext;
import com.lucifaer.jokerframework.core.context.ShellContext;
import com.lucifaer.jokerframework.core.exception.ContextTypeError;
import com.lucifaer.jokerframework.core.exception.JokerException;
import com.lucifaer.jokerframework.core.exception.ParamsNotFound;
import com.lucifaer.jokerframework.core.exception.PortAlreadyUsed;
import com.lucifaer.jokerframework.core.server.JokerServer;
import com.lucifaer.jokerframework.core.shell.ShellHelper;
import com.lucifaer.jokerframework.core.shell.ShellThrowableHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ServerFilter implements JokerFilter<JokerServer> {
    @Autowired
    JokerContext jokerContext;

    @Autowired
    ShellHelper shellHelper;

    @Autowired
    ShellThrowableHandler shellThrowableHandler;

    @Override
    public boolean basicFilter(ShellContext shellContext) throws JokerException {
        boolean basicFlag = false;
        String contextType = shellContext.getContextType();
//        校验shellcontext的类型是否为server，如果不是server则证明不是用于设置并启动server模块的context
        if ("server".equals(contextType)) {
            Map<String, String> params = shellContext.getParams();
//        校验shellcontext中是否指定了serverName（用于返回相关服务的具体实现类）
            if (params.containsKey("serverName")) {
                basicFlag = true;
            }
            else {
                throw new ParamsNotFound("serverName");
            }
        }
        else {
            throw new ContextTypeError("server", contextType);
        }
        return basicFlag;
    }

    @Override
    public List<String> paramsFilter(JokerServer server, ShellContext shellContext) {
        Map<String, String> params = shellContext.getParams();
        Map<String, String> serverParams = server.getExpectedParams();
        List<String> errorParams = new ArrayList<>();

        if (params.containsKey("serverUrl") && params.get("serverUrl") != null) {
            server.setServerUrl(params.get("serverUrl"));
        }

        if (params.containsKey("serverPort") && params.get("serverPort") != null) {
            server.setServerPort(params.get("serverPort"));
        }

        if (!jokerContext.getServerMap().values().isEmpty()) {
            try {
                if (!params.containsKey("serverPort") || params.get("serverPort").isEmpty()) {
                    params.put("serverPort", server.getServerPort());
                }
                for (Map serverInfo : jokerContext.getServerMap().values()) {
                    if (serverInfo.get("serverPort") == params.get("serverPort")) {
                        errorParams.add("serverPort");
                        throw new PortAlreadyUsed(serverInfo);
                    }
                }
            }catch (JokerException e) {
                shellThrowableHandler.handleThrowable(e);
            }
        }

        for (String i : serverParams.keySet()) {
            if (!params.containsKey(i)) {
                errorParams.add(i);
            }
            else if (params.get(i) == null) {
                errorParams.add(i);
            }
            else if ("remote_FactoryLocation".equals(i)) {
                try {
                    URL remoteFactoryLocation = new URL(params.get(i));
                } catch (MalformedURLException e) {
                    errorParams.add(i);
                    shellThrowableHandler.handleThrowable(e);
                }
            }
        }

        if (!errorParams.isEmpty()) {
            for (String e : errorParams) {
                shellHelper.getErrorMessage("Param " + e + " is error.");
            }
        }
        return errorParams;
    }
}
