package com.lucifaer.jokerframework.sdk.console;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterDescription;
import com.lucifaer.jokerframework.sdk.context.ShellContext;
import com.lucifaer.jokerframework.sdk.model.ConsoleBaseModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/10
 */
public class CommandHelper {
    private ConsoleBaseModel consoleBaseModel;
    private JCommander jCommander;

    public CommandHelper(ConsoleBaseModel consoleBaseModel) {
        this.consoleBaseModel = consoleBaseModel;
    }

    public ShellContext build(String[] argv) {
        jCommander = JCommander.newBuilder().addObject(new Object[] {consoleBaseModel}).build();
        jCommander.setUsageFormatter(new DefaultHelper(jCommander));
        jCommander.parse(argv);
        Map<String, String> map = new HashMap<>();
        ShellContext shellContext = new ShellContext();
        for (ParameterDescription pd : jCommander.getParameters()) {
            try {
                Method method = consoleBaseModel.getClass().getDeclaredMethod("get" + captureName(pd.getDescription()));
                String value = (String) method.invoke(consoleBaseModel);
                if (value == null || value.equals("")) {
                    help();
                    System.err.println(pd.getNames() + " should not be null!");
                    System.exit(1);
                }
                map.put(pd.getDescription(), value);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        shellContext.setParams(map);
        return shellContext;
    }

    public void help() {
        jCommander.usage();
    }

//    public static void main(String[] args) {
//        CommandHelper commandHelper = new CommandHelper(new DefaultConsoleModel());
//        commandHelper.build(args);
//    }

    private static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

}
