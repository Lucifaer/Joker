package com.lucifaer.jokerframework.joker.core.utils;

import com.lucifaer.jokerframework.sdk.model.ExploitModel;
import com.lucifaer.jokerframework.sdk.model.PluginModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/3
 */
public class PluginScanner<T extends PluginModel> {
    private static final Logger log = LoggerFactory.getLogger(PluginScanner.class);

    public Map<String, T> pluginsScan(String filePath) {
        Map<String, T> pluginsMap = new HashMap<>();
        File file = new File(filePath);
        if (file.exists() && file.isDirectory()) {
            Stack<File> stack = new Stack<>();
            stack.push(file);
            while (!stack.isEmpty()) {
                File path  = stack.pop();
                File[] subFile = path.listFiles(pathname -> pathname.getName().endsWith(".jar"));

                for (File jar : subFile) {
                    URL[] url = null;
                    Method method = null;
                    Boolean accessible = null;
                    String jarPath = jar.getAbsolutePath();

                    try {
                        url = new URL[] {jar.toURI().toURL()};
                        URLClassLoader classLoader = new URLClassLoader(url);
                        method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                        accessible = method.isAccessible();
                        if (!accessible) {
                            method.setAccessible(true);
                        }
                        method.invoke(classLoader, jar.toURI().toURL());

                        T pluginModel = registerPlugin(classLoader, jarPath);
                        pluginsMap.put(pluginModel.getModelName(), pluginModel);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        if (null != method && null != accessible) {
                            method.setAccessible(accessible);
                        }
                    }
                }
            }
        }
        return pluginsMap;
    }

    private T registerPlugin(URLClassLoader classLoader, String jarPath) throws IOException {
        InputStream in = classLoader.getResourceAsStream("exploitPlugin.properties");
        Properties p = new Properties();
        p.load(in);

//        由于ExploitModel继承与PluginModel，所以如果使用PluginModel初始化，则最终在map中注册的值都为PluginModel类型，而非期望的ExploitModel类型
//        这里存在一些泛用性问题，如果强制初始化ExploitModel的话，会严重影响泛用性，目前只针对ExploitPlugin的话是没有什么问题。
//        PluginModel exploitModel = new PluginModel();
        ExploitModel exploitModel = new ExploitModel();
        exploitModel.setModelName(p.getProperty("pluginName"));
        exploitModel.setReferencePath(p.getProperty("referencePath"));
        exploitModel.setClassLoader(classLoader);
        exploitModel.setJarPath(jarPath);

        return (T) exploitModel;
    }
}
