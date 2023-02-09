package com.xiaoruiit.knowledge.point.cache.redis;

import com.xiaoruiit.common.domain.Result;

import java.util.List;

/**
 * @author hanxiaorui
 * @date 2022/7/25
 */
public interface UserRedisService {
    Result initUser();

    List<User> getUserList();
}
