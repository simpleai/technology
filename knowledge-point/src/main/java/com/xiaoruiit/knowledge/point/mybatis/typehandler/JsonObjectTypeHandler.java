package com.xiaoruiit.knowledge.point.mybatis.typehandler;

import com.xiaoruiit.common.utils.JsonUtil;

/**
 * 外层直接感知模型映射
 *
 * @param <T> 领域模型
 * @author feilong.gao
 */
public class JsonObjectTypeHandler<T> extends AbsJsonObjectTypeHandler<T> {

    public JsonObjectTypeHandler(final Class<T> clazz) {
        super(clazz);
    }

    @Override
    protected T read(String json) {
        return JsonUtil.readValue(json, reference);
    }

    @Override
    protected String toJson(T obj) {
        return JsonUtil.toJson(obj);
    }

}
