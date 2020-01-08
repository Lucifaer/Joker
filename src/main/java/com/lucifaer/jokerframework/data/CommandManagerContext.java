package com.lucifaer.jokerframework.data;

public class CommandManagerContext {
    private static Boolean isUsed = false;

    public static boolean getIsUsed() {
        return CommandManagerContext.isUsed;
    }

    public static void setIsUsed(boolean flag) {
        CommandManagerContext.isUsed = flag;
    }
}
