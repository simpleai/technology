package com.xiaoruiit.knowledge.point.mybatis.mapper;

import com.xiaoruiit.knowledge.point.mybatis.domain.User;

import java.util.List;

/**
 * @author hanxiaorui
 * @date 2023/2/13
 */
public interface UserMapper {

    /**
     * 批量插入，并返回插入的主键；返回的主键在User的id上
     * mybatis3.3.1及以上的版本
     * 不能用@Param，或者只能是@Param("list")
     */
    void batchInsert(List<User> user);
}
