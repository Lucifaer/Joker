package com.lucifaer.jokerframework.joker.core.util;

import com.lucifaer.jokerframework.sdk.annotation.Exploitor;
import com.lucifaer.jokerframework.sdk.api.Exploit;
import com.lucifaer.jokerframework.sdk.context.ShellContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
        log.info(getPath());
        this.pluginsMap = loader("/Users/Lucifaer/Dropbox/VulExploit/JokerFrameWork/Joker/build/libs/exploitLib");
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
    public static void main(String[] args) {
        Exploit exploit = null;
        ClassLoaderUtil plugins = new ClassLoaderUtil();
        Map<String, Map<String, Object>> pluginMap = plugins.loader("/Users/Lucifaer/Dropbox/VulExploit/JokerFrameWork/Joker/build/libs/exploitLib");
        ShellContext shellContext = new ShellContext();
        Map<String, String> params = new HashMap<>();
        params.put("targetUrl", "127.0.0.1");
        params.put("targetPort", "7001");
        params.put("command", "open /System/Applications/Calculator.app");
        shellContext.setParams(params);

        Map<String, Object> pluginInfo = pluginMap.get("cve-2020-2555(12.2.1.4)");
        ExecutorService pool = Executors.newCachedThreadPool();
        ExploitTest2 exploitTest = new ExploitTest2(pluginInfo, shellContext);
        Future<Exploit> future = pool.submit(exploitTest);
        while (true) {
            if (future.isDone()) {
                try {
                    exploit = future.get();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                pool.shutdown();
                break;
            }
        }
        if (exploit != null) {
            Exploit3Test exploit3Test = new Exploit3Test(exploit, pluginInfo ,shellContext);
            exploit3Test.start();
        }
    }

    public Map<String, Map<String, Object>> loader(String filePath) {
        log.info("in loader");
        File file = new File(filePath);
        if (file.exists() && file.isDirectory()) {
            Stack<File> stack = new Stack<>();
            stack.push(file);
            while (!stack.isEmpty()) {
                File path = stack.pop();
                File[] subFile = path.listFiles(pathname -> pathname.getName().endsWith(".jar"));


                for (File jar : subFile) {
                    log.info(jar.getAbsolutePath());
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
                        log.info(Exploitor.class.getName());
                        log.info(String.valueOf(classList.size()));
                        for (Class<?> clazz : classList) {
                            if (Exploit.class.isAssignableFrom(clazz)) {
                                log.info(clazz.getName());
                            }
                            if (clazz.getAnnotation(Exploitor.class) != null) {
                                log.info("1");
                                String pluginName = clazz.getAnnotation(Exploitor.class).value();
                                log.info("pluginName: " + pluginName);
                                pluginsInfo.put("pluginName", pluginName);
                                log.info("referencePath: " + clazz.getName());
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
