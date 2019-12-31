package com.lucifaer.jokerframework.data;

import com.lucifaer.jokerframework.core.config.JokerConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JokerConfiguration.class)
public class ShellContextTest {
    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void testShellContextPrototype() {
        ShellContext shellContext1 = (ShellContext) applicationContext.getBean("shellContext");
        ShellContext shellContext2 = (ShellContext) applicationContext.getBean("shellContext");
        assertNotEquals(shellContext1, shellContext2);
    }
}