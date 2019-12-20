package com.lucifaer.jokerframework.core.factory;

import com.lucifaer.jokerframework.core.config.ExploitConfiguration;
import com.lucifaer.jokerframework.core.config.ShellConfiguration;
import com.lucifaer.jokerframework.data.ShellTestData;
import com.lucifaer.jokerframework.data.core.ShellDataModel;
import com.lucifaer.jokerframework.modules.Payload;
import com.lucifaer.jokerframework.modules.payloads.JmxCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ShellConfiguration.class, ExploitConfiguration.class})
public class PayloadFactoryTest {
    @Autowired
    private ShellDataModel shellContext;

    @Autowired
    private PayloadFactory payloadFactory;

    @Test
    public void getObject() throws Exception {
        Map<String, String> testParams = ShellTestData.params;
        testParams.put("exploitName", "jmx");
        testParams.put("payloadName", "command");
        shellContext.setParams(testParams);
        Payload payload = (Payload) payloadFactory.getObject();
        assertEquals(JmxCommand.class, payload.getClass());
    }
}