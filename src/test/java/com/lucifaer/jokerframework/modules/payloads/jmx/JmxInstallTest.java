package com.lucifaer.jokerframework.modules.payloads.jmx;

import com.lucifaer.jokerframework.core.mode.ExploitModeTest;
import org.junit.Test;

public class JmxInstallTest {
    @Test
    public void testExec() throws Exception {
        ExploitModeTest exploitModeTest = new ExploitModeTest();
        exploitModeTest.testInitPayload();
        JmxCommand jmxCommand = new JmxCommand();
        jmxCommand.exec();
    }
}