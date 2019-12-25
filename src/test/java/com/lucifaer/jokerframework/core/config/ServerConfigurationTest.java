package com.lucifaer.jokerframework.core.config;

import com.lucifaer.jokerframework.data.ShellTestData;
import com.lucifaer.jokerframework.data.core.ShellDataModel;
import com.lucifaer.jokerframework.server.Client;
import com.lucifaer.jokerframework.server.Server;
import com.lucifaer.jokerframework.server.http.JmxHttpServer;
import com.lucifaer.jokerframework.server.jmx.JmxClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ShellConfiguration.class, ServerConfiguration.class})
public class ServerConfigurationTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ShellDataModel shellContext;

    @Test
    public void server() {
        Map<String, String> testParams = ShellTestData.params;
        testParams.put("serverType", "http");
        testParams.put("serverName", "jmx");
        testParams.put("serverUrl", "");
        testParams.put("serverPort", "");
        shellContext.setParams(testParams);
        Server server = (Server) applicationContext.getBean("server");
        server.createServer();
        assertEquals(JmxHttpServer.class, server.getClass());
    }

    @Test
    public void client() {
        Map<String, String> testParams = ShellTestData.params;
        testParams.put("exploitName", "jmx");
        shellContext.setParams(testParams);
        Client client = (Client) applicationContext.getBean("client");
        assertEquals(JmxClient.class, client.getClass());
    }
}