package com.lucifaer.jokerframework.utils;

public class ErrorHandler {
    public static void printErroMsg(Class<?> errorClass, String msg) {
        System.err.println("[Error] " + errorClass.getPackage().getName() + " got an error: " + msg);
        System.exit(-1);
    }
}
