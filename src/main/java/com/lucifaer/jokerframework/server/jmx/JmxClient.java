package com.lucifaer.jokerframework.server.jmx;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;

import static com.lucifaer.jokerframework.utils.OutPut.echo;

public class JmxClient {
    private MBeanServerConnection mbsc;
    private String targetUrl;

    public void init() {
        String remote_jmx_server = "service:jmx:rmi:///jndi/rmi://" + targetUrl + "/jmxrmi";
        echo("[INFO] Create an RMI connector client and connect it to the RMI connector server");
        try {
            JMXServiceURL url = new JMXServiceURL(remote_jmx_server);
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
    }
    public MBeanServerConnection getMbsc() {
        return mbsc;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
