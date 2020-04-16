package com.lucifaer.jokerframework.joker.core.filter;

import com.lucifaer.jokerframework.sdk.context.ShellContext;
import com.lucifaer.jokerframework.joker.core.error.common.ContextTypeError;
import com.lucifaer.jokerframework.joker.core.error.common.ParamsNotFound;
import com.lucifaer.jokerframework.sdk.api.Model;

import java.util.Map;

/**
 * BaseFilter抽象类用于提供通用的参数预处理流程及检查工作
 * @author Lucifaer
 * @version 3.0
 */
public abstract class BaseFilter<T extends Model> {
    /**
     * basicFilter方法用于检测:
     * <p>1. ContextType是否为期望类型</p>
     * <p>2. 模块所必须设置值的选项是否已经设置了值</p>
     * @param baseModel 需要进行检测的模型，如Exploit，Server
     * @param shellContext 被检测模型对应的参数上下文
     * @throws ContextTypeError 上下文类型非期望类型
     * @see ContextTypeError
     * @throws ParamsNotFound 期望类型参数未找到
     * @see ParamsNotFound
     * @return boolean 是否通过预处理检测
     * @author Lucifaer
     * @version 3.0
    */
    public boolean basicFilter(T baseModel, ShellContext shellContext) throws ContextTypeError, ParamsNotFound {
        boolean basicFlag = false;
        String contextType = shellContext.getContextType();
        String expectedType = baseModel.getModelTypeName();
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

    /**
     * preCheck方法由于在调用baseModel的启动方法前对参数进行预处理工作，包括:
     * <p>1. 检验ContextType是否为期望类型</p>
     * <p>2. 检验模块所必须设置值的选项是否已经设置了值</p>
     * <p>3. 根据用户输入完成baseModel参数的预处理工作</p>
     * <p>4. 校验用户输入的参数是否合规</p>
     * @param baseModel 初始化调用的baseModel，如Server或Exploit
     * @param shellContext 被检测模型对应的参数上下文
     * @author Lucifaer
     * @version 3.0
     */
    public void preCheck(T baseModel, ShellContext shellContext) {}
}
