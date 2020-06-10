package com.lucifaer.jokerframework.joker.core.utils;

import com.lucifaer.jokerframework.joker.core.error.core.ModelNotFound;
import com.lucifaer.jokerframework.joker.core.factory.ExploitFactory;
import com.lucifaer.jokerframework.joker.core.shell.ShellThrowableHandler;
import com.lucifaer.jokerframework.sdk.model.ExploitModel;
import org.springframework.stereotype.Component;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/8
 */
@Component
public class ModelGetter {
    private final ExploitFactory exploitFactory;
    private final ShellThrowableHandler shellThrowableHandler;

    public ModelGetter(ExploitFactory exploitFactory, ShellThrowableHandler shellThrowableHandler) {
        this.exploitFactory = exploitFactory;
        this.shellThrowableHandler = shellThrowableHandler;
    }

    public ExploitModel getExploitModel(String exploitName) {
        ExploitModel exploitModel = null;
        try {
            for (String existModel : exploitFactory.getExistMap().keySet()) {
                if (exploitName.equals(existModel)) {
                    exploitModel = exploitFactory.getExistMap().get(existModel);
                }
            }

            if (exploitModel == null) {
                throw new ModelNotFound(exploitName);
            }
        } catch (ModelNotFound e) {
            shellThrowableHandler.handleThrowable(e);
        }
        return exploitModel;
    }
}
