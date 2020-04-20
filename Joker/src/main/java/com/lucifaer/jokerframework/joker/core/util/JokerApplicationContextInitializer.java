package com.lucifaer.jokerframework.joker.core.util;

import com.lucifaer.jokerframework.joker.Joker;
import com.lucifaer.jokerframework.sdk.annotation.Exploitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

public class JokerApplicationContextInitializer implements ApplicationContextInitializer {
    private static final Logger log = LoggerFactory.getLogger(JokerApplicationContextInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
        ClassLoader beanClassLoader = beanFactory.getBeanClassLoader();
        log.info("Exploit lib: " + getPath());
        List<Class<?>> list1 = ClassScanner.loadClass(getPath(), beanClassLoader);
        log.info("=============================Plugin Loading===============================");
        for (Class<?> clazz : list1) {
            if (clazz.getAnnotation(Exploitor.class) != null) {
                String beanName = clazz.getAnnotation(Exploitor.class).value();
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
                beanFactory.registerBeanDefinition(clazz.getAnnotation(Exploitor.class).value(), beanDefinitionBuilder.getRawBeanDefinition());
                log.info("Registered new exploit plugin: " + beanName);
            }
        }
        log.info("=============================End Loading==================================");
    }

    private static String getPath() {
        String filePath = System.getProperty("user.dir");
        String fileSeparator = System.getProperty("file.separator");
        try {
            filePath = URLDecoder.decode(filePath, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return filePath + fileSeparator + "exploitLib";
    }
}
