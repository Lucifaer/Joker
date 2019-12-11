package com.lucifaer.jokerframework.core.factory;

import com.lucifaer.jokerframework.data.BaseExploitDataModel;
import com.lucifaer.jokerframework.modules.Payload;
import com.lucifaer.jokerframework.utils.ClassUtil;
import org.springframework.beans.factory.FactoryBean;

import java.util.Map;

public class PayloadFactory implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        Map<String, Class> payloadMap = ClassUtil.getAllClassByInterface(Payload.class);
        String exploitName = BaseExploitDataModel.exploitName;
        String exploitModeName = BaseExploitDataModel.exploitModeName;

        for (String className : payloadMap.keySet()) {
            String key = className.toLowerCase();
            if (key.startsWith(exploitName+exploitModeName)) {
                return payloadMap.get(className).newInstance();
            }
        }
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return Payload.class;
    }
}
