package com.lucifaer.jokerframework.joker.core.dispatcher;

import com.lucifaer.jokerframework.joker.core.BaseModel;
import com.lucifaer.jokerframework.joker.core.error.core.ContextTypeError;
import com.lucifaer.jokerframework.joker.core.error.core.ModelNotFound;
import com.lucifaer.jokerframework.joker.core.error.core.ParamsNotFound;
import com.lucifaer.jokerframework.joker.core.factory.BaseFactory;
import com.lucifaer.jokerframework.joker.core.filter.BaseFilter;
import com.lucifaer.jokerframework.joker.core.shell.ShellThrowableHandler;
import com.lucifaer.jokerframework.sdk.context.ShellContext;
import com.lucifaer.jokerframework.sdk.model.Model;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/3
 */
public abstract class BaseDispatcher<T extends Model> {
    protected BaseModel<T> model;
    protected BaseFactory<T> factory;
    protected BaseFilter<T> filter;
    protected ShellThrowableHandler shellThrowableHandler;

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
                String modelName = shellContext.getParams().get(model.getModelType() + "Name");
                for (String existModel : factory.getExistMap().keySet()) {
                    if (modelName.equals(existModel)) {
                        baseModel = factory.getExistMap().get(existModel);
                    }
                }

                if (baseModel == null) {
                    throw new ModelNotFound(modelName);
                }
            }
        } catch (ParamsNotFound | ContextTypeError | ModelNotFound e) {
            e.printStackTrace();
        }
        return baseModel;
    }

    public Object dispatch(ShellContext shellContext) {return dispatcher(shellContext);}
}
