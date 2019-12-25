package com.lucifaer.jokerframework.utils.commons;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtil {
    public static Map<String, Class> getAllClassByAbstractClass(Class c) {
        Map<String, Class> returnClassMap = null;
        String packageName = c.getPackage().getName();
        Map<String, Class> allClass = getClasses(packageName);
        returnClassMap = new HashMap<>();
        for (Class classes : allClass.values()) {
            if (c.isAssignableFrom(classes)) {
                if (!c.equals(classes)) {
                    returnClassMap.put(classes.getSimpleName(), classes);
                }
            }
        }
        return returnClassMap;
    }

    public static Map<String, Class> getAllClassByInterface(Class c) {
        Map<String, Class> returnClassMap = null;
        if (c.isInterface()) {
            String packageName = c.getPackage().getName();
            Map<String, Class> allClass = getClasses(packageName);
            returnClassMap = new HashMap<String, Class>();
            for (Class classes : allClass.values()) {
                if (c.isAssignableFrom(classes)){
                    if (!c.equals(classes)) {
                        returnClassMap.put(classes.getSimpleName(), classes);
                    }
                }
            }
        }
        return returnClassMap;
    }

    public static Map<String, Class> getClasses(String packageName) {
        Map<String, Class> classes = new HashMap<>();
        boolean recursive = true;
        String packageDirName = packageName.replace(".", "/");
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                }
                else if ("jar".equals(protocol)) {
                    try {
                        JarFile jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
                        Enumeration<JarEntry> entries = jarFile.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            if (name.charAt(0) == '/') {
                                name = name.substring(1);
                            }
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                if (idx != -1) {
                                    packageName = name.substring(0, idx).replace('/', '.');
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {
                                            classes.put(className, Class.forName(packageName + '.' + className));
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, Map<String, Class> classes) {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }

        File[] dirfiles = dir.listFiles(pathname -> (recursive && pathname.isDirectory()) || (pathname.getName().endsWith(".class")));

        for (File file : dirfiles) {
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                        file.getAbsolutePath(),
                        recursive,
                        classes);
            }
            else {
                String className = file.getName().substring(0, file.getName().length()-6);
                try {
                    classes.put(className, Class.forName(packageName + "." + className));
                }
                catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
