package com.lucifaer.jokerframework.joker.core.task;

import com.lucifaer.jokerframework.joker.core.context.JokerContext;
import com.lucifaer.jokerframework.joker.core.dispatcher.ExploitDispatcher;
import com.lucifaer.jokerframework.joker.core.factory.ExploitFactory;
import com.lucifaer.jokerframework.joker.core.utils.ModelGetter;
import com.lucifaer.jokerframework.joker.core.utils.cache.ExploitCacheGetter;
import com.lucifaer.jokerframework.sdk.api.Exploit;
import com.lucifaer.jokerframework.sdk.context.ShellContext;
import com.lucifaer.jokerframework.sdk.model.ExploitModel;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/8
 */
@Component
public class PluginInitTask implements Task {
    private final ExploitDispatcher exploitDispatcher;
    private final ExploitCacheGetter exploitCacheGetter;
    private final ModelGetter modelGetter;

    public PluginInitTask(ExploitDispatcher exploitDispatcher, ExploitCacheGetter exploitCacheGetter, ModelGetter modelGetter) {
        this.exploitDispatcher = exploitDispatcher;
        this.exploitCacheGetter = exploitCacheGetter;
        this.modelGetter = modelGetter;
    }

    @Override
    public void createTask(ShellContext shellContext) {
        Map<String, String> params = shellContext.getParams();
        Exploit exploit = null;
        ExploitModel exploitModel = null;
        String exploitName = params.get("exploitName");
        exploit = exploitCacheGetter.getCachedExploit(exploitName);
        if (exploit == null) {
            exploit = (Exploit) exploitDispatcher.dispatch(shellContext);
            exploitCacheGetter.addCachedExploit(exploit);
        }
        exploitModel = modelGetter.getExploitModel(exploitName);

        if (params.containsKey("targetUrl")) {
            exploitModel.setTargetUrl(params.get("targetUrl"));
        }

        if (params.containsKey("targetPort")) {
            exploitModel.setTargetPort(params.get("targetPort"));
        }

        exploitModel.setRequiredParams(exploit.getRequiredParams());
        exploitModel.setDocumentation(exploit.documentation());
    }

    @Override
    public void stopTask(ShellContext shellContext) {

    }

    @Override
    public Map listTask() {
        return null;
    }
}
