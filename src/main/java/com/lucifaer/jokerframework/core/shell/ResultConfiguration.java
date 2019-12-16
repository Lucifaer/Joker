package com.lucifaer.jokerframework.core.shell;

import com.lucifaer.jokerframework.data.JokerShellDataModel.BaseJokerShellDataModel;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.ResultHandler;

import static com.lucifaer.jokerframework.data.JokerShellDataModel.BaseJokerShellDataModel.*;
import static com.lucifaer.jokerframework.utils.OutPut.echo;

@Configuration
public class ResultConfiguration implements ResultHandler<Result> {
    @Override
    public void handleResult(Result result) {
        if ("use".equals(result.getCurrentCommand())) {
            currentAttributedString = "(" + result.getResult() + ")" + defaultAttributedString;
        }
        if ("set".equals(result.getCurrentCommand())) {
            echo("set " + result.getOption() + ": " + result.getResult());
        }
    }
}
