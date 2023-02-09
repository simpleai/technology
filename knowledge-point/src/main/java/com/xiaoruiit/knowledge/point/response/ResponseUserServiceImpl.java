package com.xiaoruiit.knowledge.point.response;

import com.xiaoruiit.common.domain.Result;
import com.xiaoruiit.common.utils.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanxiaorui
 * @date 2022/12/28
 */
@Service
public class ResponseUserServiceImpl implements UserService{
    @Override
    public Result<List<?>> findByCode(String code, UserInfoDtoEnum returnType) {
        // redis或数据库查询
        List<Object> objects = new ArrayList<>();

        List<?> users = EntityUtils.copy(objects, returnType.getClazz());
        return Result.success(users);
    }
}
