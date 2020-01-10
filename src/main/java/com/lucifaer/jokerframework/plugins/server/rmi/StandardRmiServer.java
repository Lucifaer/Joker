package com.lucifaer.jokerframework.plugins.server.rmi;

import com.lucifaer.jokerframework.data.JokerContext;
import com.lucifaer.jokerframework.data.ShellContext;
import com.lucifaer.jokerframework.plugins.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.Reference;

@Component("standardRmiServer")
public class StandardRmiServer extends BaseRmiServer implements Server {
    @Autowired
    JokerContext jokerContext;

    private ShellContext shellContext;

    public void createServer() {
        this.shellContext = jokerContext.getCurrentShellContext();
        setServerPort(shellContext.getParams().get("serverPort"));
        setFactoryLocation(shellContext.getParams().get("factoryLocation"));
        setReference(this.generateReference());
        super.createServer();
    }

    @Override
    public String getServerName() {
        return "standardRmiServer";
    }

    private Reference generateReference() {
        String className = shellContext.getParams().get("referenceClassName");
        String factoryName = shellContext.getParams().get("referenceFactoryName");
        String factoryLocation = shellContext.getParams().get("referenceFactoryLocation");
        try {
            return new Reference(className, factoryName, factoryLocation);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
