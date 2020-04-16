package com.lucifaer.jokerframework.joker.core.task;

import com.lucifaer.jokerframework.sdk.context.Context;
import com.lucifaer.jokerframework.joker.core.context.JokerContext;
import com.lucifaer.jokerframework.sdk.context.ShellContext;
import com.lucifaer.jokerframework.joker.core.shell.ShellHelper;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Lucifaer
 * @version 3.0
 */
@Component
public class CommonTask implements Task {
    private final JokerContext jokerContext;
    private final ShellHelper shellHelper;

    public CommonTask(JokerContext jokerContext, ShellHelper shellHelper) {
        this.jokerContext = jokerContext;
        this.shellHelper = shellHelper;
    }

    @Override
    public void createTask(ShellContext shellContext) {
        jokerContext.sessionRegister(shellContext);
        jokerContext.currentShell = shellContext;
        shellHelper.echoSuccess("Create " + shellContext.getContextName() + " session");
    }

    @Override
    public void stopTask(ShellContext shellContext) {
        jokerContext.currentShell = null;
    }

    @Override
    public Map<String, Context> listTask() {
        return jokerContext.getSessionMap();
    }
}
