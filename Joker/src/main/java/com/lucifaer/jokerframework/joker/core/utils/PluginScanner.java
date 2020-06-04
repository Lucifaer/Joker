package com.lucifaer.jokerframework.joker.core.utils;

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

        PluginModel exploitModel = new PluginModel();
        exploitModel.setModelName(p.getProperty("pluginName"));
        exploitModel.setReferencePath(p.getProperty("referencePath"));
        exploitModel.setClassLoader(classLoader);
        exploitModel.setJarPath(jarPath);

        return (T) exploitModel;
    }
}
