package com.lucifaer.jokerframework.plugins.server.rmi;

import com.lucifaer.jokerframework.plugins.Server;
import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.NamingException;
import javax.naming.Reference;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public abstract class BaseRmiServer implements Server {
//  在rmi中的serverUrl应为空，但是为了符合接口的需求最好还是声明出来
    private String serverUrl;
    private String serverPort;
    private String factoryLocation;
    private Registry registry;
    private Reference reference;
    private ReferenceWrapper referenceWrapper;

    private void init() {

        if (serverPort == null || serverPort.isEmpty()) {
            this.serverPort = "2000";
        }

        if (factoryLocation == null || factoryLocation.isEmpty()) {
            this.factoryLocation = "http://0.0.0.0:9999";
        }
    }

    public void createServer() {
        init();
        try {
            registry = LocateRegistry.createRegistry(Integer.parseInt(this.serverPort));
            referenceWrapper = new ReferenceWrapper(reference);
            registry.bind("Exploit", referenceWrapper);
        } catch (RemoteException | NamingException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {

    }

    @Override
    public String getServerUrl() {
        return serverUrl;
    }

    @Override
    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getFactoryLocation() {
        return factoryLocation;
    }

    public void setFactoryLocation(String factoryLocation) {
        this.factoryLocation = factoryLocation;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }
}
