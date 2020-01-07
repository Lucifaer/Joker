package com.lucifaer.jokerframework.core.shell.config;

import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ValueProviderSupport;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class JokerShellProvider extends ValueProviderSupport {

    private final static String[] values = new String[] {
            "targetUrl",
            "command",
            "serverUrl",
            "serverPort",
    };

    @Override
    public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {
        return Arrays.stream(values).map(CompletionProposal::new).collect(Collectors.toList());
    }
}
