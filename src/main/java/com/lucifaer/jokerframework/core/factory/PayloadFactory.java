package com.lucifaer.jokerframework.core.factory;

import com.lucifaer.jokerframework.data.core.ShellDataModel;
import com.lucifaer.jokerframework.modules.Payload;
import com.lucifaer.jokerframework.utils.ClassUtil;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class PayloadFactory implements FactoryBean {
    @Autowired
    private ShellDataModel shellDataModel;

    @Override
    public Object getObject() throws Exception {
        Map<String, Class> payloadMap = ClassUtil.getAllClassByInterface(Payload.class);
        String exploitName = shellDataModel.getParams().get("exploitName");
        String payloadName = shellDataModel.getParams().get("payloadName");

        for (String className : payloadMap.keySet()) {
            String key = className.toLowerCase();
            if (key.startsWith(exploitName+payloadName)) {
                return payloadMap.get(className).newInstance();
            }
        }
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return Payload.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
