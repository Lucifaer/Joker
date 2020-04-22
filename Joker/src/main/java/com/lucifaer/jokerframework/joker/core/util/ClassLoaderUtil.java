package com.lucifaer.jokerframework.joker.core.util;

import com.lucifaer.jokerframework.sdk.annotation.Exploitor;
import com.lucifaer.jokerframework.sdk.context.ShellContext;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Lucifaer
 * @version 3.0
 */
public class ClassLoaderUtil {
    private Map<String, Map<String, Object>> pluginsMap = new HashMap<>();
    public ClassLoaderUtil() {
        this.pluginsMap = loader(getPath());
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

    public Map<String, Map<String, Object>> getPluginsMap() {
        return pluginsMap;
    }
//        public static void main(String[] args) {
//        Class exploit = null;
//        ClassLoaderUtil plugins = new ClassLoaderUtil();
//        Map<String, Map<String, Object>> pluginMap = plugins.loader("/Users/Lucifaer/Dropbox/VulExploit/JokerFrameWork/Joker/build/libs/exploitLib");
//        ShellContext shellContext = new ShellContext();
//        Map<String, String> params = new HashMap<>();
//        params.put("targetUrl", "127.0.0.1");
//        params.put("targetPort", "7001");
//        params.put("command", "open /System/Applications/Calculator.app");
//        shellContext.setParams(params);
//
//        Map<String, Object> pluginInfo = pluginMap.get("cve-2020-2555(12.2.1.4)");
//        ExploitTest exploitTest = new ExploitTest(pluginInfo, shellContext);
//        exploitTest.start();
//    }

    public Map<String, Map<String, Object>> loader(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isDirectory()) {
            Stack<File> stack = new Stack<>();
            stack.push(file);
            while (!stack.isEmpty()) {
                File path = stack.pop();
                File[] subFile = path.listFiles(pathname -> pathname.getName().endsWith(".jar"));


                for (File jar : subFile) {
                    URL[] url = null;
                    JarFile jarFile = null;
                    Method method = null;
                    Boolean accessible = null;
                    String className = jar.getAbsolutePath();
                    Map<String, Object> pluginsInfo = new HashMap<>();
                    List<Class<?>> classList = new ArrayList<>();

                    try {
                        url = new URL[] {jar.toURI().toURL()};
                        URLClassLoader classLoader = new URLClassLoader(url);
                        method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                        accessible = method.isAccessible();
                        if (!accessible) {
                            method.setAccessible(true);
                        }
                        method.invoke(classLoader, jar.toURI().toURL());

                        jarFile = new JarFile(new File(className));
                        Enumeration<JarEntry> entries = jarFile.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            if (name.charAt(0) == '/') {
                                name = name.substring(1);
                            }

                            name = name.replace(File.separatorChar, '.');
                            if (name.endsWith(".class") && !entry.isDirectory()) {
                                try {
                                    String className1 = name.substring(0, name.length()-6);
                                    classList.add(classLoader.loadClass(className1));
                                }catch (Throwable e) {
//                                    log.error(e.getMessage());
                                }
                            }
                        }
                        for (Class<?> clazz : classList) {
                            if (clazz.getAnnotation(Exploitor.class) != null) {
                                String pluginName = clazz.getAnnotation(Exploitor.class).value();
                                pluginsInfo.put("pluginName", pluginName);
                                pluginsInfo.put("referencePath", clazz.getName());
                            }
                        }
                        pluginsInfo.put("classLoader", classLoader);
                        pluginsMap.put((String) pluginsInfo.get("pluginName"), pluginsInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e.getMessage());
                    }
                    finally {
                        if (null != jarFile) {
                            try {
                                jarFile.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e.getMessage());
                            }
                        }
                        if (null != method && null != accessible) {
                            method.setAccessible(accessible);
                        }
                    }
                }
            }
        }
        return pluginsMap;
    }
}
