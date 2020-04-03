package com.lucifaer.jokerframework.core.task;

import com.lucifaer.jokerframework.core.context.Context;
import com.lucifaer.jokerframework.core.context.ShellContext;

import java.util.Map;

public interface JokerTask {
    void createTask(ShellContext shellContext);
    Context attachTask(String processId);
    void stopTask(ShellContext shellContext);
    Map listTask();
}
