package com.lucifaer.jokerframework.modules.payloads.jmx.data;

import com.lucifaer.jokerframework.data.JmxDataModel;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class JmxOptionsModelTest {
    @Test
    public void testInit() throws Exception {
        Map<String, String> testOptions = new HashMap<>();
        testOptions.put("exploitModeName", "install");
        testOptions.put("targetUrl", "127.0.0.1:23333");
        testOptions.put("installUrl", "http://127.0.0.1:9998/");
        testOptions.put("command", "whoami");
        JmxDataModel.init(testOptions);
        assertEquals("install", JmxDataModel.exploitModeName);
        assertEquals("127.0.0.1:23333", JmxDataModel.targetUrl);
        assertEquals("http://127.0.0.1:9998/", JmxDataModel.installUrl);
        assertEquals("whoami", JmxDataModel.command);
    }

    @Test
    public void otherInit() throws Exception {
        testInit();
        assertEquals("install", JmxDataModel.exploitModeName);
        assertEquals("127.0.0.1:23333", JmxDataModel.targetUrl);
        assertEquals("http://127.0.0.1:9998/", JmxDataModel.installUrl);
        assertEquals("whoami", JmxDataModel.command);
    }
}