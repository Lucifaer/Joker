package com.lucifaer.jokerframework.server.jmx;

import com.lucifaer.jokerframework.data.core.ShellDataModel;
import com.lucifaer.jokerframework.server.Client;
import org.springframework.beans.factory.annotation.Autowired;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;

import static com.lucifaer.jokerframework.utils.commons.Output.echo;

public class JmxClient implements Client {
    @Autowired
    private ShellDataModel shellContext;

    private MBeanServerConnection mbsc;

    @Override
    public Object init() {
        String remoteJmxServer = "service:jmx:rmi:///jndi/rmi://" + shellContext.getParams().get("targetUrl") + "/jmxrmi";
        echo("[INFO] Create an RMI connector client and connect it to the RMI connector server");
        try {
            JMXServiceURL url = new JMXServiceURL(remoteJmxServer);
            echo("[INFO] Connecting to: " + url);
            JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
            echo("[INFO] Get an MBeanServerConnection");
            this.mbsc = jmxc.getMBeanServerConnection();
        } catch (MalformedURLException e) {
            echo("[ERROR] Fail to generate JMXServiceURL");
            System.exit(-1);
        } catch (IOException e) {
            echo("[ERROR] Can't connect to remote service");
            System.exit(-1);
        }
        return mbsc;
    }
}
