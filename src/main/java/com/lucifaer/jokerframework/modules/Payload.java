package com.lucifaer.jokerframework.modules;

import java.util.Map;

public interface Payload {
    void exploit();
    void init(Map<String, String> params);
}
