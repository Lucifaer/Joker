package com.lucifaer.jokerframework.joker.core.shell.command.helper;

import com.lucifaer.jokerframework.joker.core.shell.ShellHelper;
import com.lucifaer.jokerframework.joker.core.utils.ModelGetter;
import com.lucifaer.jokerframework.sdk.context.ShellContext;
import com.lucifaer.jokerframework.sdk.model.ExploitModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/9
 */
@Component
public class DocumentationHelper {
    private ShellHelper shellHelper;
    private ModelGetter modelGetter;

    public DocumentationHelper(ShellHelper shellHelper, ModelGetter modelGetter) {
        this.shellHelper = shellHelper;
        this.modelGetter = modelGetter;
    }

    public List<String> exploitDocumentationGenerator(ShellContext shellContext) {
        List<String> documentResult = new ArrayList<>();
        Map<String, String> params = shellContext.getParams();
        String exploitName = params.get("exploitName");
        ExploitModel exploitModel = modelGetter.getExploitModel(exploitName);
        List<String> requiredParams = exploitModel.getRequiredParams();

        documentResult.add(exploitName + " configurations: ");

        if (exploitModel.getDocumentation() != null) {
            documentResult.add("Description: ");
            for (String[] description : exploitModel.getDocumentation()) {
                documentResult.addAll(Arrays.asList(description));
            }
            documentResult.add("-------------------------------------------------------------------------------------");
        }

        for (String key : requiredParams) {
            switch (key) {
                case "targetUrl":
                    documentResult.add("   [targetUrl]                 " + params.getOrDefault("targetUrl", null));
                    break;
                case "targetPort":
                    documentResult.add("   [targetPort]                 " + params.getOrDefault("targetPort", null));
                    break;
                case "command":
                    documentResult.add("   [command]                   " + params.getOrDefault("command", null));
                    break;
                default:
                    documentResult.add("   [" + key + "]                   " + params.getOrDefault(key, null));
            }
        }

        return documentResult;
    }
}
