package com.xiaoruiit.knowledge.point.mybatis.typehandler.nest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xiaoruiit.common.utils.JsonUtil;
import com.xiaoruiit.knowledge.point.mybatis.domain.UserEntityMappingDatabase;
import com.xiaoruiit.knowledge.point.mybatis.typehandler.JsonTypeHandler;

import java.util.List;

public class AddressDetailListTypeHandler extends JsonTypeHandler<List<UserEntityMappingDatabase.AddressDetail>> {

    public AddressDetailListTypeHandler() {
        super(new TypeReference<List<UserEntityMappingDatabase.AddressDetail>>() {
        });
    }

    @Override
    protected List<UserEntityMappingDatabase.AddressDetail> read(String json) {
        return JsonUtil.readValue(json, reference);
    }

    @Override
    protected String toJson(List<UserEntityMappingDatabase.AddressDetail> noteInfoList) {
        return JsonUtil.toJson(noteInfoList);
    }

}
