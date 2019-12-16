package com.lucifaer.jokerframework.data;

import java.util.HashMap;
import java.util.Map;

import static com.lucifaer.jokerframework.utils.ErrorHandler.printErroMsg;

public abstract class DataModel {
//    框架上下文
    private static Map<String, Object> jokerContext;

    static {
        jokerContext = new HashMap<>();
    }

    String paramConfirm(Map<String, String> map, String key) {
        if (map.get(key).isEmpty()) {
            printErroMsg(this.getClass(), key + " is null");
        }
        return map.get(key);
    }

    public static Map<String, Object> getJokerContext() {
        return jokerContext;
    }

    public static void setJokerContext(String key, Object value) {
        jokerContext.put(key, value);
    }
}
