package com.lucifaer.jokerframework.sdk.model;

import java.net.URLClassLoader;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/3
 */
public class PluginModel implements Model {
    protected String modelType = "plugin";
    protected String modelName;
    protected String referencePath;
    protected URLClassLoader classLoader;
    protected String jarPath;

    public Object getPlugin() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return classLoader.loadClass(referencePath).newInstance();
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getReferencePath() {
        return referencePath;
    }

    public void setReferencePath(String referencePath) {
        this.referencePath = referencePath;
    }

    public URLClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(URLClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    @Override
    public String getModelType() {
        return modelType;
    }

    @Override
    public String getModelName() {
        return modelName;
    }
}
