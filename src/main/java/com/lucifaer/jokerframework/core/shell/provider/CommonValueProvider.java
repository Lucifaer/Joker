package com.lucifaer.jokerframework.core.shell.provider;

import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ValueProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ShellComponent
public class CommonValueProvider implements ValueProvider {
    @Override
    public boolean supports(MethodParameter parameter, CompletionContext completionContext) {
        return true;
    }

    @Override
    public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {
        List<CompletionProposal> result = new ArrayList<>();
        String[] options = {
                "payloadName",
                "targetUrl",
                "command",
                "serverType",
                "serverName",
                "serverUrl",
                "serverPort",
        };
        List<String> knownOptions = new ArrayList<>(Arrays.asList(options));

        String userInput = completionContext.currentWordUpToCursor();
        knownOptions.stream()
                    .filter(t -> t.startsWith(userInput))
                    .forEach(t -> result.add(new CompletionProposal(t)));
        return result;
    }
}
