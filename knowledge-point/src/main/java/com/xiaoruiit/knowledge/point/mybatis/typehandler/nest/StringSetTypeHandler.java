package com.xiaoruiit.knowledge.point.mybatis.typehandler.nest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xiaoruiit.common.utils.JsonUtil;
import com.xiaoruiit.knowledge.point.mybatis.typehandler.JsonTypeHandler;

import java.util.Set;

public class StringSetTypeHandler extends JsonTypeHandler<Set<String>> {

    public StringSetTypeHandler() {
        super(new TypeReference<Set<String>>() {
        });
    }

    @Override
    protected Set<String> read(String json) {
        return JsonUtil.of(json, reference);
    }

    @Override
    protected String toJson(Set<String> list) {
        return JsonUtil.toJson(list);
    }

}
