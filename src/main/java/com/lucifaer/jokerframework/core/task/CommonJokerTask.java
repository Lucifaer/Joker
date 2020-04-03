package com.lucifaer.jokerframework.core.task;

import com.lucifaer.jokerframework.core.context.Context;
import com.lucifaer.jokerframework.core.context.JokerContext;
import com.lucifaer.jokerframework.core.context.ShellContext;
import com.lucifaer.jokerframework.core.exception.SessionContextNameAlreadyUsed;
import com.lucifaer.jokerframework.core.shell.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommonJokerTask implements JokerTask {
    @Autowired
    JokerContext jokerContext;

    @Autowired
    ShellHelper shellHelper;

    @Override
    public void createTask(ShellContext shellContext) {
        try {
            jokerContext.sessionRegister(shellContext);
        }catch (SessionContextNameAlreadyUsed e) {
            shellHelper.getErrorMessage(e.toString());
        }
        jokerContext.currentShell = shellContext;
        shellHelper.getSuccessMessage("Create " + shellContext.getContextName() + " process");
    }

    @Override
    public Context attachTask(String processId) {
        return (Context) jokerContext.getContextMap().get("session").get(processId);
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
