package com.lucifaer.jokerframework.joker.core.util;

import com.lucifaer.jokerframework.sdk.context.ShellContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

/**
 * @author Lucifaer
 * @version 3.0
 */
public class LoadingRegisterUtil {
    private static final Logger log = LoggerFactory.getLogger(ClassLoaderUtil.class);
    private Map<String, Map<String, Object>> pluginsMap = new HashMap<>();

    public static void main(String[] args) {
        LoadingRegisterUtil loadingRegisterUtil = new LoadingRegisterUtil();
        Map<String, Map<String, Object>> map = loadingRegisterUtil.preScan("/Users/Lucifaer/Dropbox/VulExploit/exploitLib/cve-2020-2555(12_2_1_3)/build/libs/");
        log.info(String.valueOf(map));
        ShellContext shellContext = new ShellContext();
        Map<String, String> params = new HashMap<>();
        params.put("targetUrl", "127.0.0.1");
        params.put("targetPort", "7001");
        params.put("command", "open /System/Applications/Calculator.app");
        shellContext.setParams(params);

        TestExploit testExploit = new TestExploit(map.get("cve-2020-2555(12.2.1.3)"), shellContext);
        testExploit.exploit();
    }

    public LoadingRegisterUtil() {
        log.info("插件目录为：" + getPath());
        this.pluginsMap = preScan(getPath());
    }

    public Map<String, Map<String, Object>> getPluginsMap() {
        if (pluginsMap.isEmpty()) {
            log.warn("未找到漏洞插件，请核对插件目录是否正确！");
        }
        return pluginsMap;
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

    public Map<String, Map<String, Object>> preScan(String filePath) {
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

                        registerPlugin(classLoader, jarPath, pluginsMap);
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

    static void registerPlugin(URLClassLoader classLoader, String jarPath, Map<String, Map<String, Object>> pluginsMap) throws IOException {
        Map<String, Object> pluginsInfo = new HashMap<>();
        InputStream in = classLoader.getResourceAsStream("exploitPlugin.properties");
        Properties p = new Properties();
        p.load(in);

        pluginsInfo.put("pluginName", p.getProperty("pluginName"));
        pluginsInfo.put("referencePath", p.getProperty("referencePath"));
        pluginsInfo.put("classLoader", classLoader);
        pluginsInfo.put("jarPath", jarPath);
        pluginsMap.put((String) pluginsInfo.get("pluginName"), pluginsInfo);
    }
}
