package com.lucifaer.jokerframework.joker.core.filter;

import com.lucifaer.jokerframework.joker.core.error.core.ContextTypeError;
import com.lucifaer.jokerframework.joker.core.error.core.ParamsNotFound;
import com.lucifaer.jokerframework.sdk.context.ShellContext;
import com.lucifaer.jokerframework.sdk.model.Model;

import java.util.Map;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/3
 */
public abstract class BaseFilter<T extends Model> {
    public boolean basicFilter(T baseModel, ShellContext shellContext) throws ParamsNotFound, ContextTypeError {
        boolean basicFlag = false;
        String contextType = shellContext.getContextType();
        String expectedType = baseModel.getModelType();
        if (expectedType.equals(contextType)) {
            Map<String, String> params = shellContext.getParams();
            if (params.containsKey(expectedType + "Name")) {
                basicFlag = true;
            }
            else {
                throw new ParamsNotFound(expectedType + "Name");
            }
        }
        else {
            throw new ContextTypeError(expectedType, contextType);
        }
        return basicFlag;
    }

    public void preCheck(T baseModel, ShellContext shellContext) {}
}
