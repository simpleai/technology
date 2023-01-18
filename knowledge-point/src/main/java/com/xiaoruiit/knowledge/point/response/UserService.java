package com.xiaoruiit.knowledge.point.response;

import com.xiaoruiit.knowledge.point.common.Result;

import java.util.List;

/**
 * @author hanxiaorui
 * @date 2022/12/28
 */
public interface UserService {
    Result<List<?>> findByCode(String code, UserInfoDtoEnum returnType);
}
