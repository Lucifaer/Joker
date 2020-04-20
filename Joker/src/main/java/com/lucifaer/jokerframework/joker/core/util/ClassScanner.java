package com.lucifaer.jokerframework.joker.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassScanner {
    private static final Logger log = LoggerFactory.getLogger(ClassScanner.class);

    public static List<Class<?>> loadClass(String filePath, ClassLoader beanClassLoader) {
        List<Class<?>> classList = new ArrayList<>();
        File file = new File(filePath);
        if (file.exists() && file.isDirectory()) {
            Stack<File> stack = new Stack<>();
            stack.push(file);
            while (!stack.isEmpty()) {
                File path = stack.pop();
                File[] classFile = path.listFiles(pathname -> pathname.isDirectory() || pathname.getName().endsWith(".class") || pathname.getName().endsWith(".jar"));

                for (File subFile : classFile) {
                    if (subFile.isDirectory()) {
                        stack.push(subFile);
                    }
                    else {
                        URL url = null;
                        JarFile jar = null;
                        Method method = null;
                        Boolean accessible = null;
                        String className = subFile.getAbsolutePath();

                        try {
                            URLClassLoader classLoader = (URLClassLoader) beanClassLoader;
                            method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                            accessible = method.isAccessible();
                            if (!accessible) {
                                method.setAccessible(true);
                            }

                            if (className.endsWith(".class")) {
                                url = file.toURI().toURL();
                                method.invoke(classLoader, url);

                                int clazzPathLen = file.getAbsolutePath().length()+1;
                                className = className.substring(clazzPathLen, className.length()-6);
                                className = className.replace(File.separatorChar, '.');
                                classList.add(classLoader.loadClass(className));
                            }
                            else if (className.endsWith(".jar")) {
                                log.info("ClassScanner loading plugins: ");
                                log.warn("These class have a dependency error, but if you use part of them, it will be fine.");
                                url = subFile.toURI().toURL();
                                method.invoke(classLoader, url);

                                jar = new JarFile(new File(className));
                                Enumeration<JarEntry> entries = jar.entries();
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
                                                log.warn("* " + name);
                                            }
                                        }
                                }

                            }
                        } catch (Exception e) {
                            log.error(e.getMessage());
                            throw new RuntimeException(e.getMessage());
                        }
                        finally {
                            if (null != jar) {
                                try {
                                    jar.close();
                                } catch (IOException e) {
                                    log.error(e.getMessage());
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
        }
        return classList;
    }
}
