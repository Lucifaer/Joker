package com.lucifaer.jokerframework.joker.core.error.common;

import com.lucifaer.jokerframework.joker.core.error.JokerException;

/**
 * 处理参数不存在的异常
 * @author Lucifaer
 * @version 3.0
 */
public class ParamsNotFound extends JokerException {
    public ParamsNotFound(String expectedParam) {
        super(expectedParam + " is not found");
    }
}
