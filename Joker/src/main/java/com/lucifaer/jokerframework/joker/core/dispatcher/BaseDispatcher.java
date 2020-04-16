package com.lucifaer.jokerframework.joker.core.dispatcher;

import com.lucifaer.jokerframework.joker.core.BaseModel;
import com.lucifaer.jokerframework.sdk.context.ShellContext;
import com.lucifaer.jokerframework.joker.core.error.common.ContextTypeError;
import com.lucifaer.jokerframework.joker.core.error.common.ParamsNotFound;
import com.lucifaer.jokerframework.joker.core.error.core.ModelNotFound;
import com.lucifaer.jokerframework.joker.core.factory.BaseFactory;
import com.lucifaer.jokerframework.joker.core.filter.BaseFilter;
import com.lucifaer.jokerframework.joker.core.shell.ShellThrowableHandler;
import com.lucifaer.jokerframework.sdk.api.Model;

/**
 * @author Lucifaer
 * @version 3.0
 */
public abstract class BaseDispatcher<T extends Model> {
    private final BaseModel<T> model;
    private final BaseFactory<T> factory;
    protected final BaseFilter<T> filter;
    private final ShellThrowableHandler shellThrowableHandler;

    public BaseDispatcher(BaseModel<T> model, BaseFactory<T> factory, BaseFilter<T> filter, ShellThrowableHandler shellThrowableHandler) {
        this.model = model;
        this.factory = factory;
        this.filter = filter;
        this.shellThrowableHandler = shellThrowableHandler;
    }

    public T dispatcher(ShellContext shellContext) {
        T baseModel = null;
        try {
            if (filter.basicFilter((T) model, shellContext)) {
                String modelName = shellContext.getParams().get(model.getModelTypeName() + "Name");
                for (String existModel : factory.getExistMap().keySet()) {
                    if (modelName.equals(existModel)) {
                        baseModel = factory.getExistMap().get(modelName);
                    }
                }

                if (baseModel == null) {
                    throw new ModelNotFound(modelName);
                }
//                else {
//                    filter.preCheck(baseModel, shellContext);
//                }
            }
        } catch (ModelNotFound | ParamsNotFound | ContextTypeError e) {
            shellThrowableHandler.handleThrowable(e);
        }
        return baseModel;
    }
}
