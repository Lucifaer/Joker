package com.lucifaer.jokerframework.plugins.client.jmx;

import com.lucifaer.jokerframework.core.shell.config.JokerShellHelper;
import com.lucifaer.jokerframework.data.JokerContext;
import com.lucifaer.jokerframework.data.ShellContext;
import com.lucifaer.jokerframework.plugins.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;

@Component("jmxClient")
public class JmxClient implements Client {
    @Autowired
    private JokerContext jokerContext;

    @Autowired
    JokerShellHelper jokerShellHelper;

    private MBeanServerConnection mbsc;

    @Override
    public Object init() {
        ShellContext shellContext = jokerContext.getCurrentShellContext();
        String remoteJmxServer = "service:jmx:rmi:///jndi/rmi://" + shellContext.getParams().get("targetUrl") + "/jmxrmi";
        try {
            JMXServiceURL url = new JMXServiceURL(remoteJmxServer);
            jokerShellHelper.echoInfo("Connecting to " + url);
            JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
            jokerShellHelper.echoInfo("Get an MBeanServerConnection");
            this.mbsc = jmxc.getMBeanServerConnection();
        } catch (MalformedURLException e) {
            jokerShellHelper.echoError("Fail to generate JMXServiceURL");
            System.exit(-1);
        } catch (IOException e) {
            jokerShellHelper.echoError("Can't connect to remote service");
            System.exit(-1);
        }
        return mbsc;
    }
}
