package com.lucifaer.jokerframework.utils;

import com.lucifaer.jokerframework.data.BaseExploitDataModel;
import org.junit.Test;

import java.util.Map;

public class ClassUtilTest {

    @Test
    public void getModelAllClassByInterface() {
        Map<String, Class> modelMap = ClassUtil.getAllClassByAbstractClass(BaseExploitDataModel.class);

    }
}