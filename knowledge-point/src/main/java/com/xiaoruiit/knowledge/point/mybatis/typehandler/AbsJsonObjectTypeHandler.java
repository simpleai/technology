package com.xiaoruiit.knowledge.point.mybatis.typehandler;

import com.fasterxml.jackson.core.type.TypeReference;

import java.lang.reflect.Type;

/**
 * @author hanxiaorui
 * @date 2024/10/17
 */
public abstract class AbsJsonObjectTypeHandler<T> extends JsonTypeHandler<T> {
    public AbsJsonObjectTypeHandler(final Class<T> clazz) {
        super(new TypeReference<T>() {
            public Type getType() {
                return clazz;
            }
        });
    }
}
