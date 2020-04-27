package com.lucifaer.jokerframework.joker.core.util;

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
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Lucifaer
 * @version 3.0
 */
public class ClassLoaderUtil {
    private static final Logger log = LoggerFactory.getLogger(ClassLoaderUtil.class);
    private Map<String, Map<String, Object>> pluginsMap = new HashMap<>();
    public ClassLoaderUtil() {
        log.info("插件目录为：" + getPath());
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
        if (pluginsMap.isEmpty()) {
            log.warn("未找到漏洞插件，请核对插件目录是否正确！");
        }
        return pluginsMap;
    }

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
//                    List<Class<?>> classList = new ArrayList<>();

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
//                                    classList.add(classLoader.loadClass(className1));
                                    classLoader.loadClass(className1);
                                }catch (Throwable e) {
//                                    log.error(e.getMessage());
                                }
                            }
                        }

                        InputStream in = classLoader.getResourceAsStream("exploitPlugin.properties");
                        Properties p = new Properties();
                        p.load(in);
//                        log.info(p.getProperty("pluginName"));
//                        for (Class<?> clazz : classList) {
//                            if (clazz.getAnnotation(Exploitor.class) != null) {
//                                String pluginName = clazz.getAnnotation(Exploitor.class).value();
//                                pluginsInfo.put("pluginName", pluginName);
//                                pluginsInfo.put("referencePath", clazz.getName());
//                            }
//                        }
                        pluginsInfo.put("pluginName", p.getProperty("pluginName"));
                        pluginsInfo.put("referencePath", p.getProperty("referencePath"));
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
