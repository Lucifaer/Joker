package com.lucifaer.jokerframework.joker.core.shell.command;

import com.lucifaer.jokerframework.joker.core.context.JokerContext;
import com.lucifaer.jokerframework.joker.core.shell.ShellHelper;
import com.lucifaer.jokerframework.joker.core.shell.command.helper.DocumentationHelper;
import com.lucifaer.jokerframework.joker.core.utils.ModelGetter;
import com.lucifaer.jokerframework.sdk.context.ShellContext;
import com.lucifaer.jokerframework.sdk.model.ExploitModel;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/8
 */
@ShellComponent
public class ShowOptionsCommand {
    private JokerContext jokerContext;
    private DocumentationHelper documentationHelper;
    private ShellHelper shellHelper;
    private ModelGetter modelGetter;

    public ShowOptionsCommand(JokerContext jokerContext, DocumentationHelper documentationHelper, ShellHelper shellHelper, ModelGetter modelGetter) {
        this.jokerContext = jokerContext;
        this.documentationHelper = documentationHelper;
        this.shellHelper = shellHelper;
        this.modelGetter = modelGetter;
    }

    @ShellMethod(value = "show exploit configurations", key = "show_options", group = "Common")
    public void show_options() {
        ShellContext shellContext = (ShellContext) jokerContext.getCurrentShell();
        List<String> document = documentationHelper.exploitDocumentationGenerator(shellContext);
        for (String s : document) {
            shellHelper.echoDocument(s);
        }
//        ExploitModel exploitModel = modelGetter.getExploitModel(exploitName);
//        for (String[] option : exploitModel.getDocumentation()) {
//            for (String s: option) {
//                shellHelper.echoDocument(s);
//            }
//        }
    }
}
