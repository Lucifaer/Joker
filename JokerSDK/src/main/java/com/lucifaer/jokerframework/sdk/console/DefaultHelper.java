package com.lucifaer.jokerframework.sdk.console;

import com.beust.jcommander.IUsageFormatter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterDescription;
import com.beust.jcommander.internal.Lists;

import java.util.List;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/11
 */
public class DefaultHelper implements IUsageFormatter {
    private JCommander commander;

    public DefaultHelper(JCommander commander) {
        this.commander = commander;
    }

    @Override
    public void usage(String commandName) {

    }

    @Override
    public void usage(String commandName, StringBuilder out) {

    }

    @Override
    public void usage(StringBuilder out) {
        if (commander.getDescriptions() == null) {
            commander.createDescriptions();
        }

        List<ParameterDescription> params = Lists.newArrayList();
        params.addAll(commander.getFields().values());

        if (params.size() > 0) {
            out.append("Options:\n");

            for (ParameterDescription pd : params) {
                out.append("  ")
                    .append(pd.getNames())
                    .append("\t\t\t")
                    .append(pd.getDescription()).append("\n");
            }
        }
    }

    @Override
    public void usage(String commandName, StringBuilder out, String indent) {

    }

    @Override
    public void usage(StringBuilder out, String indent) {
        if (commander.getDescriptions() == null) {
            commander.createDescriptions();
        }

        List<ParameterDescription> params = Lists.newArrayList();
        params.addAll(commander.getFields().values());

        if (params.size() > 0) {
            out.append("Options:\n");

            for (ParameterDescription pd : params) {
                out.append(pd.getNames()).append("\n");
            }
        }
    }

    @Override
    public String getCommandDescription(String commandName) {
        return null;
    }
}
