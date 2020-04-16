package com.lucifaer.jokerframework.joker.core.dispatcher;

import com.lucifaer.jokerframework.sdk.context.ShellContext;
import com.lucifaer.jokerframework.joker.server.LdapServer;
import com.lucifaer.jokerframework.joker.server.RmiServer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lucifaer
 * @version 3.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = {InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=" + false})
public class BaseDispatcherTest {
    @Autowired
    ServerDispatcher serverDispatcher;

    @Autowired
    ShellContext shellContext;

    @Autowired
    LdapServer ldapServer;

    @Autowired
    RmiServer rmiServer;

    @Test
    public void getLdapServer() {
        shellContext.setContextName("ldap-test");
        shellContext.setContextType("server");
        Map<String, String> params = new HashMap<>();
        params.put("serverName", "ldap-server");
        params.put("remote_className", "Exploit");
        params.put("remote_factoryLocation", "http://127.0.0.1:9999");
        shellContext.setParams(params);

        Assert.assertEquals(serverDispatcher.startDispatcher(shellContext).getClass(), ldapServer.getClass());
    }

    @Test
    public void getRmiServer() {
        shellContext.setContextName("rmi-test");
        shellContext.setContextType("server");
        Map<String, String> params = new HashMap<>();
        params.put("serverName", "rmi-server");
        params.put("remote_className", "Exploit");
        params.put("remote_factoryName", "Exploit");
        params.put("remote_factoryLocation", "http://127.0.0.1:9999");
        shellContext.setParams(params);

        Assert.assertEquals(serverDispatcher.startDispatcher(shellContext).getClass(), rmiServer.getClass());
    }
}