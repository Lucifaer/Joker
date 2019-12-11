package com.lucifaer.jokerframework.modules;

import com.lucifaer.jokerframework.data.BaseExploitDataModel;

public interface Payload {
//    void setPayloadOptions(BaseExploitDataModel payloadOptions);
    void exec();
    void init(Object object);
}
