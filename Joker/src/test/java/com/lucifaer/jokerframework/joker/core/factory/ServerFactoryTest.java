package com.lucifaer.jokerframework.joker.core.factory;

import com.lucifaer.jokerframework.joker.core.server.Server;
import com.lucifaer.jokerframework.joker.server.LdapServer;
import com.lucifaer.jokerframework.joker.server.RmiServer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;
/**
 * ServerFactoryTest类用于测试ServerFactory的功能
 * @author Lucifaer
 * @version 3.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = { InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=" + false })
public class ServerFactoryTest {
    @Autowired
    BaseFactory<Server> serverFactory;

    @Autowired
    LdapServer ldapServer;

    @Autowired
    RmiServer rmiServer;

    @Test
    public void getServerFactory() {
        Map<String, Server> serverMap = serverFactory.getExistMap();
        Assert.assertTrue(serverMap.containsKey("ldap-server"));
        Assert.assertTrue(serverMap.containsKey("rmi-server"));
        Assert.assertEquals(serverMap.get("ldap-server").getClass(), ldapServer.getClass());
        Assert.assertEquals(serverMap.get("rmi-server").getClass(), rmiServer.getClass());
    }
}