package com.lucifaer.jokerframework.joker.core.task;

import com.lucifaer.jokerframework.sdk.context.ShellContext;

import java.util.Map;

/**
 * JokerTask接口负责对外提供Task相关方法，包括启动任务、关闭任务、列举任务
 * @author Lucifaer
 * @version 3.0
 */
public interface Task {
    /**
     * createTask方法用于创建新的任务，主要的执行流程如下:
     * <p>1. 根据传入的shellContext，调用Dispatcher（分派器）获取到相关的BaseModel</p>
     * <p>2. 根据BaseModel所需要的参数调用相关的Filter进行参数预处理及检查</p>
     * <p>3. 调用BaseModel具体实现的任务启动方法，启动任务</p>
     * @param shellContext 参数上下文
     * @author Lucifaer
     * @version 3.0
    */
    void createTask(ShellContext shellContext);

    /**
     * stopTask方法用于停止任务，并删除相关注册项
     * @param shellContext 参数上下文
     * @author Lucifaer
     * @version 3.0
    */
    void stopTask(ShellContext shellContext);
    
    /**
     * listTask方法用于展示相关BaseModel的具体实现
     * @return java.util.Map BaseModel的具体实现
     * @author Lucifaer
     * @version 3.0
    */
    Map listTask();
}
