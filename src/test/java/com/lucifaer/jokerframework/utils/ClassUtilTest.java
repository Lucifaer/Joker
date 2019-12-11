package com.lucifaer.jokerframework.utils;

import com.lucifaer.jokerframework.data.BaseDataModel;
import com.lucifaer.jokerframework.data.BaseExploitDataModel;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class ClassUtilTest {

    @Test
    public void getModelAllClassByInterface() {
        Map<String, Class> modelMap = ClassUtil.getAllClassByAbstractClass(BaseExploitDataModel.class);

    }
}