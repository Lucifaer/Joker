package com.lucifaer.jokerframework.data;

import com.lucifaer.jokerframework.core.config.JokerConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JokerConfiguration.class)
public class JokerContextTest {
    @Autowired
    JokerContext jokerContext;

    @Autowired
    ShellContext shellContext;

    @Test
    public void testShellRegister() {

    }
}