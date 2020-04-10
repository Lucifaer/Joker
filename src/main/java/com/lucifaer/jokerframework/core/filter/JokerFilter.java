package com.lucifaer.jokerframework.core.filter;

import com.lucifaer.jokerframework.core.context.ShellContext;
import com.lucifaer.jokerframework.core.exception.JokerException;
import com.lucifaer.jokerframework.core.server.JokerServer;

import java.util.List;

public interface JokerFilter<T> {
    boolean basicFilter(ShellContext shellContext) throws JokerException;
    List<String> paramsFilter(T jokerObj, ShellContext shellContext);
}
